package com.liuyuan.chinaoracle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyuan.chinaoracle.annotation.AuthCheck;
import com.liuyuan.chinaoracle.common.DeleteRequest;
import com.liuyuan.chinaoracle.common.response.BaseResponse;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.common.response.ResultUtils;
import com.liuyuan.chinaoracle.config.WxOpenConfig;
import com.liuyuan.chinaoracle.constant.UserConstant;
import com.liuyuan.chinaoracle.exception.BusinessException;
import com.liuyuan.chinaoracle.exception.ThrowUtils;
import com.liuyuan.chinaoracle.model.dto.user.*;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.vo.LoginUserVO;
import com.liuyuan.chinaoracle.model.vo.UserVO;
import com.liuyuan.chinaoracle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private WxOpenConfig wxOpenConfig;

    // region 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求体
     * @return 统一响应结果
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String email = userRegisterRequest.getEmail();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(email, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(email, password, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求体
     * @param exchange         代表一次HTTP请求和响应的完整过程的对象
     * @return 统一响应结果
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                                               ServerWebExchange exchange) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(email, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(email, password, exchange);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 用户登录(微信开放平台)
     *
     * @param exchange 代表一次HTTP请求和响应的完整过程的对象
     * @param code     todo 不知道这是啥
     * @return 统一响应结果
     */
    @GetMapping("/login/wx_open")
    public BaseResponse<LoginUserVO> userLoginByWxOpen(ServerWebExchange exchange,
                                                       @RequestParam("code") String code) {
        WxOAuth2AccessToken accessToken;
        try {
            WxMpService wxService = wxOpenConfig.getWxMpService();
            accessToken = wxService.getOAuth2Service().getAccessToken(code);
            WxOAuth2UserInfo userInfo = wxService.getOAuth2Service().getUserInfo(accessToken, code);
            String unionId = userInfo.getUnionId();
            String mpOpenId = userInfo.getOpenid();
            if (StringUtils.isAnyBlank(unionId, mpOpenId)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
            }
            return ResultUtils.success(userService.userLoginByMpOpen(userInfo, exchange));
        } catch (Exception e) {
            log.error("userLoginByWxOpen error", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
        }
    }

    /**
     * 用户注销
     *
     * @param exchange 代表一次HTTP请求和响应的完整过程的对象
     * @return 统一响应结果
     */
    @PostMapping("/logout")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Boolean> userLogout(ServerWebExchange exchange) {
        if (exchange == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(exchange);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param exchange 代表一次HTTP请求和响应的完整过程的对象
     * @return 统一响应结果
     */
    @GetMapping("/get/login")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<LoginUserVO> getLoginUser(ServerWebExchange exchange) {
        User user = userService.getLoginUser(exchange);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    // endregion


    // region 用户信息相关(user权限)

    /**
     * 更新个人信息（用户）
     *
     * @param userUpdateMineRequest 用户更新个人信息请求体
     * @param exchange              代表一次HTTP请求和响应的完整过程的对象
     * @return 统一响应结果
     */
    @PostMapping("/update/my")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMineRequest userUpdateMineRequest,
                                              ServerWebExchange exchange) {
        if (userUpdateMineRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(exchange);
        User user = new User();
        BeanUtils.copyProperties(userUpdateMineRequest, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取用户包装类
     *
     * @param id 用户ID
     * @return 统一响应结果
     */
    @GetMapping("/get/vo")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<UserVO> getUserVOById(long id) {
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest 用户查询请求体
     * @return 统一响应结果
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<User> userPage = userService.page(new Page<>(current, size),
            userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
        userVOPage.setRecords(userVO);
        return ResultUtils.success(userVOPage);
    }

    // endregion


    // region 增删改查(admin权限)

    /**
     * 创建用户（仅管理员）
     *
     * @param userAddRequest 用户创建请求体
     * @return 统一响应结果
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 删除用户（仅管理员）
     *
     * @param deleteRequest 删除请求体
     * @return 统一响应结果
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户（仅管理员）
     *
     * @param userUpdateRequest 用户更新请求体
     * @return 统一响应结果
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id 用户ID
     * @return 统一响应结果
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest 用户查询请求体
     * @return 统一响应结果
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, size),
            userService.getQueryWrapper(userQueryRequest));
        return ResultUtils.success(userPage);
    }

    // endregion
}
