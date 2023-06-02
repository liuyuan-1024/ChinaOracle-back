package com.liuyuan.chinaoracle.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求体(仅管理员)
 */
@Data
public class UserUpdateRequest implements Serializable {

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