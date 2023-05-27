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
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 全称(自定义名称、昵称)
     */
    private String fullName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 密码哈希算法
     */
    private String passwdHashAlgo;
    /**
     *
     */
    private Integer mustChangePassword;
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
     * 盐
     */
    private String salt;
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
    private Long lastLoginUnix;
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
     *
     */
    private Integer allowGitHook;
    /**
     * 允许本地导入
     */
    private Integer allowImportLocal;
    /**
     * 允许创建远程分支
     */
    private Integer allowCreateOrganization;
    /**
     * 禁止登录
     */
    private Integer prohibitLogin;
    /**
     * 头像
     */
    private String avatar;
    /**
     * email头像
     */
    private String avatarEmail;
    /**
     * 用户自定义头像
     */
    private Integer useCustomAvatar;
    /**
     * 可见度
     */
    private Integer visibility;
    /**
     * 仓库管理员修改团队权限
     */
    private Integer repoAdminChangeTeamAccess;
    /**
     * 不同视图样式
     */
    private String diffViewStyle;
    /**
     * 主题(默认: auto)
     */
    private String theme;
    /**
     * 活动保密
     */
    private Integer keepActivityPrivate;
}