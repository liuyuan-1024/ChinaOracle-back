package com.liuyuan.chinaoracle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyuan.chinaoracle.model.dto.user.UserQueryRequest;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.vo.LoginUserVO;
import com.liuyuan.chinaoracle.model.vo.UserVO;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * @author 源
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-05-06 02:58:49
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param email         用户邮箱
     * @param password      用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String email, String password, String checkPassword);

    /**
     * 用户登录
     *
     * @param email    用户邮箱
     * @param password 用户密码
     * @param exchange WebFlux框架中的一个核心接口, 代表了一个客户端与服务器之间的交互
     *                 (即一次HTTP请求和响应的完整过程),并提供了一系列方法来对请求和响应进行处理。
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String email, String password, ServerWebExchange exchange);

    /**
     * 用户登录（微信开放平台）
     *
     * @param wxOAuth2UserInfo 从微信获取的用户信息
     * @param exchange         WebFlux框架中的一个核心接口, 代表了一个客户端与服务器之间的交互
     *                         (即一次HTTP请求和响应的完整过程),并提供了一系列方法来对请求和响应进行处理。
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLoginByMpOpen(WxOAuth2UserInfo wxOAuth2UserInfo, ServerWebExchange exchange);

    /**
     * 获取当前登录用户
     *
     * @param exchange WebFlux框架中的一个核心接口, 代表了一个客户端与服务器之间的交互
     *                 (即一次HTTP请求和响应的完整过程),并提供了一系列方法来对请求和响应进行处理。
     * @return
     */
    User getLoginUser(ServerWebExchange exchange);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param exchange WebFlux框架中的一个核心接口, 代表了一个客户端与服务器之间的交互
     *                 (即一次HTTP请求和响应的完整过程),并提供了一系列方法来对请求和响应进行处理。
     * @return
     */
    User getLoginUserPermitNull(ServerWebExchange exchange);

    /**
     * 是否为管理员
     *
     * @param exchange WebFlux框架中的一个核心接口, 代表了一个客户端与服务器之间的交互
     *                 (即一次HTTP请求和响应的完整过程),并提供了一系列方法来对请求和响应进行处理。
     * @return
     */
    boolean isAdmin(ServerWebExchange exchange);

    /**
     * 是否为管理员
     *
     * @param user 用户
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 是否被封禁
     *
     * @param user 用户
     * @return
     */
    boolean isBan(User user);

    /**
     * 用户注销
     *
     * @param exchange WebFlux框架中的一个核心接口, 代表了一个客户端与服务器之间的交互
     *                 (即一次HTTP请求和响应的完整过程),并提供了一系列方法来对请求和响应进行处理。
     * @return
     */
    boolean userLogout(ServerWebExchange exchange);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user 用户
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList 用户列表
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求体
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
