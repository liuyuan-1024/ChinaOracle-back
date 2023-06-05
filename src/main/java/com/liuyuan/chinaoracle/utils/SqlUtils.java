package com.liuyuan.chinaoracle.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL 工具.
 */
public final class SqlUtils {
    private SqlUtils() {
    }

    /**
     * 校验排序字段是否合法（防止 SQL 注入）.
     *
     * @param sortField 排序字段
     * @return true=合法 false=非法
     */
    public static boolean verifySortField(final String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
