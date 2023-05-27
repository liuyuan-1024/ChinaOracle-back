package com.liuyuan.chinaoracle.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * gitea 仓库的用户表
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 小写 name
     */
    private String lowerName;
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
     * 所在地区
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
     * 创建者
     */
    private Long createdUnix;
    /**
     * 更新者
     */
    private Long updatedUnix;
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
     * 是否为系统管理员
     */
    private Integer isAdmin;
    /**
     * 是否受限
     */
    private Integer isRestricted;
    /**
     * 允许GitHook
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
     * 追随者数量
     */
    private Integer numFollowers;
    /**
     *
     */
    private Integer numFollowing;
    /**
     * star数量
     */
    private Integer numStars;
    /**
     * 仓库数量
     */
    private Integer numRepos;
    /**
     * 团队数量
     */
    private Integer numTeams;
    /**
     * 成员数量
     */
    private Integer numMembers;
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