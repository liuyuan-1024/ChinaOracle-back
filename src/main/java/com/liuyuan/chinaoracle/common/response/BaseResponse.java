package com.liuyuan.chinaoracle.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应类(通用返回类).
 *
 * @param <T> 数据泛型, 可以返回各种数据
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 状态码.
     */
    private int code;

    /**
     * 返回数据.
     */
    private T data;

    /**
     * 提示信息.
     */
    private String message;

    /**
     * 全参构造器.
     *
     * @param code    状态码
     * @param data    数据
     * @param message 信息
     */
    public BaseResponse(final int code, final T data, final String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 构造统一响应结果.
     *
     * @param code 状态码
     * @param data 数据
     */
    public BaseResponse(final int code, final T data) {
        this(code, data, "");
    }

    /**
     * 错误响应构造器.
     *
     * @param errorCode 错误码
     */
    public BaseResponse(final ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
