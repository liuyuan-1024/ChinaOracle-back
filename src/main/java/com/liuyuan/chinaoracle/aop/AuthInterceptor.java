package com.liuyuan.chinaoracle.aop;

import com.liuyuan.chinaoracle.annotation.AuthCheck;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.exception.BusinessException;
import com.liuyuan.chinaoracle.model.entity.Role;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.enums.RoleEnum;
import com.liuyuan.chinaoracle.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验 AOP.
 */
@Aspect
@Component
public class AuthInterceptor {

    /**
     * 用户服务接口.
     */
    @Resource
    private UserService userService;

    /**
     * 执行拦截.
     *
     * @param joinPoint JoinPoint类，用来获取代理类和被代理类的信息，
     *                  ProceedingJoinPoint 继承了JoinPoint,
     *                  ProceedingJoinPoint在JoinPoint的基础上暴露出 proceed 方法。
     *                  环绕通知 = 前置 + 目标方法执行 + 后置通知，
     *                  proceed方法就是用于启动目标方法的执行,暴露出这个方法，
     *                  就能支持 aop:around这种切面。
     * @param auth      权限校验注解
     * @return 连接点执行后的返回结果
     */
    @Around("@annotation(auth)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint,
                                AuthCheck auth) throws Throwable {

        // 必需权限为空时，无需校验用户权限，直接放行
        if (ObjectUtils.isEmpty(auth)) {
            return joinPoint.proceed();
        }

        RoleEnum mustRoleEnum = RoleEnum.getEnumByRole(auth.mustRole());

        // 必需权限非真实存在，可能是controller接口中@AuthCheck的mustRole赋值错误
        if (ObjectUtils.isEmpty(mustRoleEnum)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "必需权限不存在,"
                + "严查开发人员");
        }

        RequestAttributes requestAttributes =
            RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request =
            ((ServletRequestAttributes) requestAttributes).getRequest();

        // 当前登录用户
        User loginUser = userService.getLoginUser(request);

        // 用户被封号，直接拒绝
        if (userService.isBan(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 比较用户权限的优先级
        Role loginUserRole = userService.getUserRole(loginUser.getId());
        RoleEnum loginUserRoleEnum =
            RoleEnum.getEnumByRole(loginUserRole.getName());
        assert loginUserRoleEnum != null;
        if (RoleEnum.isPriority(loginUserRoleEnum, mustRoleEnum)) {
            return joinPoint.proceed();
        }

        throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
    }
}

