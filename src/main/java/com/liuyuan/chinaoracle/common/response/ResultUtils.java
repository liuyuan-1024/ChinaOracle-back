package com.liuyuan.chinaoracle.common.response;

import org.apache.ibatis.jdbc.Null;

/**
 * 响应结果工具类.
 */
public final class ResultUtils {

    private ResultUtils() {
    }

    /**
     * 成功.
     *
     * @param <T>  数据的泛型
     * @param data 待返回的数据
     * @return 统一响应类对象
     */
    public static <T> BaseResponse<T> success(final T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), data,
            ErrorCode.SUCCESS.getMessage());
    }

    /**
     * 失败.
     *
     * @param errorCode 失败码
     * @return 统一响应类对象
     */
    public static BaseResponse<Null> error(final ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败.
     *
     * @param errorCode 失败码
     * @param message   自定义消息
     * @return 统一响应类对象
     */
    public static BaseResponse<Null> error(final ErrorCode errorCode,
                                           final String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }

    /**
     * 失败.
     * 一般不允许返回自定义code, 只有在全局异常处理器中才会使用此方法
     *
     * @param code    自定义code
     * @param message 自定义消息
     * @return 统一响应类对象
     */
    public static BaseResponse<Null> error(final int code,
                                           final String message) {
        return new BaseResponse<>(code, null, message);
    }
}
