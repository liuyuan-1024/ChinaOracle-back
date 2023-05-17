package com.liuyuan.chinaoracle.filter;

import com.liuyuan.chinaoracle.annotation.AuthCheck;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.exception.BusinessException;
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
        //    // 当前登录用户拥有的权限的枚举对象
        UserRoleEnum loginUserRoleEnum = UserRoleEnum.getEnumByRole(userService.getLoginUser(exchange).getUserRole());

        // 用户权限不存在 或 用户被封号，直接拒绝
        if (ObjectUtils.isEmpty(loginUserRoleEnum) || UserRoleEnum.BAN.equals(loginUserRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 校验请求是否有级别足够的权限
        return handlerMapping.getHandler(exchange).switchIfEmpty(chain.filter(exchange))
            .flatMap(handler -> {
                if (handler instanceof HandlerMethod) {
                    HandlerMethod methodHandle = (HandlerMethod) handler;
                    AuthCheck auth = methodHandle.getMethodAnnotation(AuthCheck.class);
                    // 必需权限不为空
                    if (ObjectUtils.isNotEmpty(auth)) {
                        // 目标权限的枚举对象
                        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByRole(auth.mustRole());
                        // 目标权限存在时
                        if (ObjectUtils.isNotEmpty(mustRoleEnum)) {
                            // 如果 userRole的优先级低于mustRole的 则抛出异常
                            if (!UserRoleEnum.isPriority(loginUserRoleEnum, mustRoleEnum)) {
                                return Mono.error(new BusinessException(ErrorCode.NO_AUTH_ERROR));
                            }
                        }
                    }
                }

                // 通过权限校验，放行
                return chain.filter(exchange);
            });
    }
}
