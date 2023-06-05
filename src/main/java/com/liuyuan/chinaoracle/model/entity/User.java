package com.liuyuan.chinaoracle.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表.
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID.
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 邮箱.
     */
    private String email;
    /**
     * 密码.
     */
    private String password;
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
    /**
     * 权限等级, 数值越大权限越大.
     */
    private Integer role;
    /**
     * 0-未封禁 1-被封禁.
     */
    private Integer isBan;
    /**
     * 创建时间.
     */
    private Date createdAt;
    /**
     * 最后更新时间.
     */
    private Date updatedAt;
    /**
     * 是否已删除(0-未删除 1-已删除).
     */
    private Integer isDeleted;
}
