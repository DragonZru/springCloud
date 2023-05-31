package com.spring.web.config;

import com.spring.web.exception.GlobalException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    //@LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .errorHandler(errorHandler())
                //设置超时时间
                .setConnectTimeout(Duration.ofSeconds(1))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
        return restTemplate;
    }

    public ResponseErrorHandler errorHandler() {
        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().isError();
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //handle error. 这里4xx & 5xx 统一返回错误信息，不做特殊处理
                if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    throw new GlobalException(response.getStatusCode().value(), new String(response.getBody().readAllBytes()));
                } else throw new GlobalException(response.getStatusCode().value(), "unknown error.");
            }
        };
        return errorHandler;
    }
}
