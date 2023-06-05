package com.liuyuan.chinaoracle.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户视图（脱敏）.
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID.
     */
    private Long id;

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
