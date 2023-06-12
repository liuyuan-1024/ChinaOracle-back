package com.liuyuan.chinaoracle.model.enums;

import com.liuyuan.chinaoracle.constant.UserConstant;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 用户角色枚举.
 * 角色权限优先级: 数字越小, 优先级越高
 */
public enum RoleEnum {

    /**
     * 超级管理员.
     */
    SUPER_ADMIN(UserConstant.SUPER_ADMIN_ROLE, 0),
    /**
     * 管理员.
     */
    ADMIN(UserConstant.ADMIN_ROLE, 1),
    /**
     * 用户(默认角色).
     */
    USER(UserConstant.DEFAULT_ROLE, 2);

    /**
     * 角色.
     */
    private final String role;
    /**
     * 优先级.
     */
    private final Integer value;

    RoleEnum(String role, int value) {
        this.role = role;
        this.value = value;
    }

    /**
     * 根据 role 获取枚举.
     *
     * @param role 具体权限
     * @return 对应的用户角色枚举类对象
     */
    public static RoleEnum getEnumByRole(String role) {
        if (ObjectUtils.isEmpty(role)) {
            return null;
        }
        for (RoleEnum anEnum : RoleEnum.values()) {
            if (anEnum.role.equals(role)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 判断当前权限是否优先于必需权限.
     *
     * @param roleEnum     当前权限
     * @param mustRoleEnum 必需权限
     * @return true or false
     */
    public static boolean isPriority(RoleEnum roleEnum, RoleEnum mustRoleEnum) {
        return roleEnum.getValue() - mustRoleEnum.getValue() <= 0;
    }

    /**
     * 获取角色.
     *
     * @return 角色
     */
    public String getRole() {
        return role;
    }

    /**
     * 获取角色优先级.
     *
     * @return 角色优先级
     */
    public Integer getValue() {
        return value;
    }

}
