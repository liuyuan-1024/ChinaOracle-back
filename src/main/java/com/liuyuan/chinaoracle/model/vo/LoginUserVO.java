package com.liuyuan.chinaoracle.model.vo;

import lombok.Data;

import javax.security.auth.Subject;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

/**
 * 已登录用户视图（脱敏）
 **/
@Data
public class LoginUserVO implements Principal, Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}