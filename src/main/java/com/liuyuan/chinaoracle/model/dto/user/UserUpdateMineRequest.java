package com.liuyuan.chinaoracle.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新个人信息请求体
 */
@Data
public class UserUpdateMineRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 全称(自定义名称、昵称)
     */
    private String fullName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 保持email私密
     */
    private Integer keepEmailPrivate;
    /**
     * email通知首选项
     */
    private String emailNotificationsPreference;
    /**
     * 密码
     */
    private String passwd;
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