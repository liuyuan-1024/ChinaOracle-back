package com.liuyuan.chinaoracle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyuan.chinaoracle.model.dto.user.UserQueryRequest;
import com.liuyuan.chinaoracle.model.entity.Role;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.vo.LoginUserVO;
import com.liuyuan.chinaoracle.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liuyuan-1024
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-05-06 02:58:49
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册.
     *
     * @param email         用户邮箱
     * @param password      用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String email, String password, String checkPassword);

    /**
     * 用户登录.
     *
     * @param email    用户邮箱
     * @param password 用户密码
     * @param request  请求
     * @return 当前登录用户视图
     */
    LoginUserVO userLogin(String email, String password,
                          HttpServletRequest request);

    /**
     * 用户注销.
     *
     * @param request 请求
     * @return true=注销成功 false=注销失败
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户.
     *
     * @param request 请求
     * @return 用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）.
     *
     * @param request 请求
     * @return 用户
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 获取用户权限的枚举对象.
     *
     * @param userId 用户ID
     * @return 用户角色
     */
    Role getUserRole(Long userId);


    /**
     * 是否为超级管理员.
     *
     * @param userId 用户ID
     * @return true=是 false=不是
     */
    boolean verifySuperAdmin(Long userId);


    /**
     * 是否为管理员.
     *
     * @param userId 用户ID
     * @return true=是 false=不是
     */
    boolean verifyAdmin(Long userId);


    /**
     * 是否被封禁.
     *
     * @param userId 用户ID
     * @return true=被封禁 false=为封禁
     */
    boolean verifyBan(Long userId);

    /**
     * 获取脱敏的已登录用户信息.
     *
     * @param user 用户
     * @return 当前登录用户视图
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息.
     *
     * @param user 用户
     * @return 用户视图
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息.
     *
     * @param userList 用户列表
     * @return 用户视图列表
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件.
     *
     * @param userQueryRequest 用户查询请求体
     * @return 条件查询器
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
