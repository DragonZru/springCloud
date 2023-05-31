package com.spring.web.config;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {


    /**
     * @Author ylli
     * @Description webClient 是 restTemplate 的替代方案，支持高效的非阻塞和异步方法，可视为sync restTemplate.
     * @See https://github.com/eugenp/tutorials/tree/master/spring-reactive-modules/spring-reactive
     */

    @Bean
    public WebClient webClient() {
        WebClient client = WebClient.builder().clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient
                                        .create()
                                        //连接超时
                                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                        //响应超时
                                        .responseTimeout(Duration.ofSeconds(5))
                        )
                )
                .build();
        return client;
    }
}
