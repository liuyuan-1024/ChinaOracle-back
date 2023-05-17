package com.liuyuan.chinaoracle.common.file;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求体
 */
@Data
public class UploadFileRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    private static final long serialVersionUID = 1L;
}