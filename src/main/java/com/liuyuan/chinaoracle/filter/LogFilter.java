package com.liuyuan.chinaoracle.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * 请求响应日志 过滤器
 */
@Component
@Slf4j
public class LogFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        // 获取请求
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求主机名称
        String host = request.getURI().getHost();
        // 获取请求地址
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        // 获取请求参数
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String reqParam = "[" + StringUtils.join(queryParams, ", ") + "]";
        // 输出请求日志
        log.info("request start，id: {}, path: {}, ip: {}, params: {}", requestId, host,
            remoteAddress, reqParam);
        // 输出响应日志
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("request end, id: {}, cost: {}ms", requestId, totalTimeMillis);
        return chain.filter(exchange);
    }
}
