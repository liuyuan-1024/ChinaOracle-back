package com.liuyuan.chinaoracle.exception;

import com.liuyuan.chinaoracle.common.response.ErrorCode;

/**
 * 自定义异常类.
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码.
     */
    private final int code;

    public BusinessException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(final ErrorCode errorCode, final String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
