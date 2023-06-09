package com.liuyuan.chinaoracle.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求体.
 */
@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 依据id删除.
     */
    private Long id;
}
