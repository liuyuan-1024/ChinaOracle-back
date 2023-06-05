package com.liuyuan.chinaoracle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.constant.CommonConstant;
import com.liuyuan.chinaoracle.constant.UserConstant;
import com.liuyuan.chinaoracle.exception.BusinessException;
import com.liuyuan.chinaoracle.mapper.RoleMapper;
import com.liuyuan.chinaoracle.mapper.UserMapper;
import com.liuyuan.chinaoracle.model.conversion.UserConvert;
import com.liuyuan.chinaoracle.model.dto.user.UserQueryRequest;
import com.liuyuan.chinaoracle.model.entity.Role;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.enums.RoleEnum;
import com.liuyuan.chinaoracle.model.vo.LoginUserVO;
import com.liuyuan.chinaoracle.model.vo.UserVO;
import com.liuyuan.chinaoracle.service.UserService;
import com.liuyuan.chinaoracle.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.liuyuan.chinaoracle.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 源
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-05-06 02:58:49
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    /**
     * 盐值，混淆密码.
     */
    private static final String SALT = "ChinaOracle";

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(final String email, final String password,
                             final String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(email, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (email.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (email.intern()) {
            // 账户不能重复
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, email);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword =
                DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 获取默认角色
            Role defaultRole =
                roleMapper.selectOneByName(UserConstant.DEFAULT_ROLE);
            // 3. 插入数据
            User user = new User();
            user.setEmail(email);
            user.setPassword(encryptPassword);
            user.setRole(defaultRole.getId());
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,
                    "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(final String email, final String password,
                                 HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(email, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (email.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword =
            DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        queryWrapper.eq(User::getPassword, encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 用户被封禁
        if (this.isBan(user.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    /**
     * 用户注销登录.
     *
     * @param request 请求
     * @return 是否注销成功
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 查看用户登录态，判断用户是否已登录
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    /**
     * 获取当前登录用户.
     *
     * @param request 请求
     * @return 返回当前登录用户
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 获取当前登录用户（允许未登录）.
     *
     * @param request 请求
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        return this.getById(currentUser.getId());
    }

    @Override
    public Role getUserRole(Long userId) {
        User user = userMapper.selectById(userId);
        return roleMapper.selectById(user.getRole());
    }

    @Override
    public boolean isSuperAdmin(Long userId) {
        Role role = this.getUserRole(userId);
        return RoleEnum.SUPER_ADMIN.getRole().equals(role.getName());
    }

    @Override
    public boolean isAdmin(Long userId) {
        Role role = this.getUserRole(userId);
        return RoleEnum.ADMIN.getRole().equals(role.getName());
    }

    @Override
    public boolean isBan(Long userId) {
        User user = userMapper.selectById(userId);
        return UserConstant.BAN.equals(user.getIsBan());
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        return ObjectUtils.isEmpty(user) ? null :
            UserConvert.INSTANCE.toLoginUserVo(user);
    }

    @Override
    public UserVO getUserVO(final User user) {
        return ObjectUtils.isEmpty(user) ? null
            : UserConvert.INSTANCE.toUserVo(user);
    }

    @Override
    public List<UserVO> getUserVO(final List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(final UserQueryRequest
                                                  userQueryRequest) {

        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long id = userQueryRequest.getId();
        String nickName = userQueryRequest.getNickName();
        String profile = userQueryRequest.getProfile();
        String sortField = userQueryRequest.getSortField();
        boolean isAsc = userQueryRequest.getSortOrder()
            .equals(CommonConstant.SORT_ORDER_ASC);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(id != null, User::getId, id);
        queryWrapper.lambda().like(StringUtils.isNotBlank(profile),
            User::getProfile, profile);
        queryWrapper.lambda().like(StringUtils.isNotBlank(nickName),
            User::getNickName, nickName);
        queryWrapper.orderBy(SqlUtils.verifySortField(sortField), isAsc,
            sortField);

        return queryWrapper;
    }
}
