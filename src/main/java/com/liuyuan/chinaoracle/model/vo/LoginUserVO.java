package com.liuyuan.chinaoracle.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户视图（脱敏）
 **/
@Data
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 简介
     */
    private String profile;

}