package com.liuyuan.chinaoracle.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName repository
 */
@TableName(value = "repository")
@Data
public class Repository implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 拥有者ID
     */
    private Long ownerId;

    /**
     * 拥有者name(User.name)
     */
    private String ownerName;

    /**
     * 小写name
     */
    private String lowerName;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 网站
     */
    private String website;

    /**
     * 远程服务类型
     */
    private Integer originalServiceType;

    /**
     * 远程url
     */
    private String originalUrl;

    /**
     * 默认分支
     */
    private String defaultBranch;

    /**
     * 观察者数量
     */
    private Integer numWatches;

    /**
     * star数量
     */
    private Integer numStars;

    /**
     * 拷贝分支数量
     */
    private Integer numForks;

    /**
     * 问题数量
     */
    private Integer numIssues;

    /**
     * 已关闭问题的数量
     */
    private Integer numClosedIssues;

    /**
     * 拉取数量
     */
    private Integer numPulls;

    /**
     * 已关闭拉取的数量
     */
    private Integer numClosedPulls;

    /**
     * 里程碑数量
     */
    private Integer numMilestones;

    /**
     * 已关闭里程碑的数量
     */
    private Integer numClosedMilestones;

    /**
     * 项目数量
     */
    private Integer numProjects;

    /**
     * 已关闭项目的数量
     */
    private Integer numClosedProjects;

    /**
     *
     */
    private Integer numActionRuns;

    /**
     *
     */
    private Integer numClosedActionRuns;

    /**
     * 是否私有
     */
    private Integer isPrivate;

    /**
     * 是否为空
     */
    private Integer isEmpty;

    /**
     * 是否已存档
     */
    private Integer isArchived;

    /**
     * 是否镜像
     */
    private Integer isMirror;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 允许fork
     */
    private Integer isFork;

    /**
     * 拷贝分支的ID
     */
    private Long forkId;

    /**
     * 是否为模板
     */
    private Integer isTemplate;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 大小
     */
    private Long size;

    /**
     *
     */
    private Integer isFsckEnabled;

    /**
     *
     */
    private Integer closeIssuesViaCommitInAnyBranch;

    /**
     * 话题
     */
    private String topics;

    /**
     * 信任模型
     */
    private Integer trustModel;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建者
     */
    private Long createdUnix;

    /**
     * 更新者
     */
    private Long updatedUnix;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}