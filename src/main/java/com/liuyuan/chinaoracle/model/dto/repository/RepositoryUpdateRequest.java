package com.liuyuan.chinaoracle.model.dto.repository;

import java.io.Serializable;

/**
 * 仓库更新请求体
 */
public class RepositoryUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * 仓库的描述信息
     */
    private String description;
    /**
     * 仓库主要使用的编程语言
     */
    private Integer languageId;
    /**
     * 仓库的README文件内容
     */
    private String readme;
    /**
     * 开源许可证类型
     */
    private Integer licenseId;
}
