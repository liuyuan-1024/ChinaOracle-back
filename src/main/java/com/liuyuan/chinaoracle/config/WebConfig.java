package com.liuyuan.chinaoracle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author: BugOS-ly
 * @Date: 2023/5/16 19:49
 * @Description:
 */
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl("localhost:3000/api/v1")
            .defaultHeader("Authorization", "token 961ea81253e5c593511f196346a7b1aceb53dd22")
            .defaultHeader("accept", "application/json")
            .build();
    }

    /**
     * 全局跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // WebFluxConfigurer.super.addCorsMappings(registry);
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
