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

    /**
     * 构造业务异常.
     *
     * @param code    状态码
     * @param message 信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造业务异常.
     *
     * @param errorCode 自定义错误码
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 构造业务异常.
     *
     * @param errorCode 自定义错误码
     * @param message   信息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    /**
     * 获取业务异常的状态码.
     *
     * @return 状态码
     */
    public int getCode() {
        return code;
    }
}
