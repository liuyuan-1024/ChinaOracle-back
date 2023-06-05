package com.liuyuan.chinaoracle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置类.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * cors跨域配置.
     *
     * @param registry 协助注册基于URL模式的全局CorsConfiguration映射的实体对象
     */
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        // 覆盖所有请求
        registry.addMapping("/**")
            // 允许发送 Cookie
            .allowCredentials(true)
            // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("*");
    }
}
