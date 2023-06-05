package com.liuyuan.chinaoracle.constant;

/**
 * 用户常量.
 */
public interface UserConstant {

    /**
     * 用户登录态键.
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 角色 与role表中的name字段的值保持一致

    /**
     * 超级管理员.
     */
    String SUPER_ADMIN_ROLE = "super_admin";

    /**
     * 管理员.
     */
    String ADMIN_ROLE = "admin";

    /**
     * 默认角色.
     */
    String DEFAULT_ROLE = "user";

    /**
     * 封禁.
     */
    Integer BAN = 1;
}
