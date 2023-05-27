package com.liuyuan.chinaoracle.model.enums;

import com.liuyuan.chinaoracle.constant.UserConstant;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 用户角色枚举
 * 角色权限优先级: 数字越小, 优先级越高
 */
public enum UserRoleEnum {

    SUPER_ADMIN("超级管理员", UserConstant.SUPER_ADMIN_ROLE, 0),
    ADMIN("系统管理员", UserConstant.ADMIN_ROLE, 1),
    USER("普通用户", UserConstant.DEFAULT_ROLE, 2);


    private final String text;
    private final String role;
    private final Integer value;

    UserRoleEnum(String text, String role, int value) {
        this.text = text;
        this.role = role;
        this.value = value;
    }

    /**
     * 根据 role 获取枚举
     *
     * @param role 具体权限
     * @return 对应的用户角色枚举类对象
     */
    public static UserRoleEnum getEnumByRole(String role) {
        if (ObjectUtils.isEmpty(role)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.role.equals(role)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 判断当前权限是否优先于必需权限
     *
     * @param role     当前权限
     * @param mustRole 必需权限
     * @return true or false
     */
    public static boolean isPriority(UserRoleEnum role, UserRoleEnum mustRole) {
        return role.getValue() - mustRole.getValue() <= 0;
    }

    public String getText() {
        return text;
    }

    public String getRole() {
        return role;
    }

    public Integer getValue() {
        return value;
    }

}
