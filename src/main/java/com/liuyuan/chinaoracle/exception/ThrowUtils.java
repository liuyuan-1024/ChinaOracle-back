package com.liuyuan.chinaoracle.exception;

import com.liuyuan.chinaoracle.common.response.ErrorCode;

/**
 * 抛异常工具类.
 */
public final class ThrowUtils {

    private ThrowUtils() {
    }

    /**
     * 条件成立则抛异常.
     *
     * @param condition        是否抛出异常
     * @param runtimeException 运行时异常
     */
    public static void throwIf(final boolean condition,
                               final RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常.
     *
     * @param condition 是否抛出异常
     * @param errorCode 自定义错误码
     */
    public static void throwIf(final boolean condition,
                               final ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常.
     *
     * @param condition 是否抛出异常
     * @param errorCode 自定义错误码
     * @param message   信息
     */
    public static void throwIf(final boolean condition,
                               final ErrorCode errorCode,
                               final String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
