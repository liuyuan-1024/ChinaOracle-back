package com.liuyuan.chinaoracle.filter;

import com.liuyuan.chinaoracle.annotation.AuthCheck;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.exception.BusinessException;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.enums.UserRoleEnum;
import com.liuyuan.chinaoracle.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 权限校验
 */
@Component
public class AuthFilter implements WebFilter {

    // 关键，通过RequestMappingHandlerMapping 我们可以获取到MethodHandler
    @Resource
    private RequestMappingHandlerMapping handlerMapping;

    @Resource
    private UserService userService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // 当前登录用户
        User loginUser = userService.getLoginUser(exchange);

        // 用户被封号，直接拒绝
        if (userService.isProhibitLogin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 校验当前登录用户是否有足够的权限
        return handlerMapping.getHandler(exchange).switchIfEmpty(chain.filter(exchange))
            .flatMap(handler -> {
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;

                    // 获取方法的注解
                    AuthCheck auth = handlerMethod.getMethodAnnotation(AuthCheck.class);

                    // 必需权限为空时，无需校验用户权限，直接放行
                    if (ObjectUtils.isEmpty(auth)) {
                        return chain.filter(exchange);
                    }

                    UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByRole(auth.mustRole());

                    // 必需权限非真实存在，可能是controller接口中@AuthCheck的mustRole赋值错误
                    if (ObjectUtils.isEmpty(mustRoleEnum)) {
                        return Mono.error(new BusinessException(ErrorCode.SYSTEM_ERROR, "必需权限不存在," +
                            "严查开发人员"));
                    }

                    // 比较用户权限的优先级
                    UserRoleEnum userRoleEnum = userService.getUserRole(loginUser);
                    if (UserRoleEnum.isPriority(userRoleEnum, mustRoleEnum)) {
                        return chain.filter(exchange);
                    }

                    return Mono.error(new BusinessException(ErrorCode.NO_AUTH_ERROR));
                }

                // todo 如果handler不是HandlerMethod对象，要不要直接放行
                return Mono.error(new BusinessException(ErrorCode.SYSTEM_ERROR));
            });
    }
}
