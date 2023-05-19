package com.liuyuan.chinaoracle.model.dto.user;

import com.liuyuan.chinaoracle.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户查询请求体
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 全称(自定义名称、昵称)
     */
    private String fullName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     *
     */
    private Long loginSource;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 位置
     */
    private String location;

    /**
     * 网站
     */
    private String website;

    /**
     *
     */
    private String rands;

    /**
     * 语言
     */
    private String language;

    /**
     * 描述
     */
    private String description;

    /**
     *
     */
    private Integer lastRepoVisibility;

    /**
     *
     */
    private Integer maxRepoCreation;

    /**
     * 活跃状态
     */
    private Integer isActive;

    /**
     * 是否受限
     */
    private Integer isRestricted;

    /**
     * 活动保密
     */
    private Integer keepActivityPrivate;

    private static final long serialVersionUID = 1L;
}