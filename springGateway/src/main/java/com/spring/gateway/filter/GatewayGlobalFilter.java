package com.spring.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    //TODO 参考 - ModifyRequestBodyGatewayFilterFactory 实现打印所有请求参数

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("GatewayGlobalFilter do");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
