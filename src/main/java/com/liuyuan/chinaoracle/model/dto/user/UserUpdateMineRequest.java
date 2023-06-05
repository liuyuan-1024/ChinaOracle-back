package com.liuyuan.chinaoracle.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新个人信息请求体.
 */
@Data
public class UserUpdateMineRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱.
     */
    private String email;

    /**
     * 昵称.
     */
    private String nickName;

    /**
     * 头像.
     */
    private String avatar;

    /**
     * 简介.
     */
    private String profile;
}
