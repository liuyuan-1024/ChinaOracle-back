package com.liuyuan.chinaoracle.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表.
 *
 * @TableName role
 */
@TableName(value = "role")
@Data
public class Role implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID.
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称.
     */
    private String name;
    /**
     * 角色描述.
     */
    private String description;
    /**
     * 角色状态.
     */
    private Integer status;
    /**
     * 创建时间.
     */
    private Date createdAt;
    /**
     * 最后更新时间.
     */
    private Date updatedAt;

}