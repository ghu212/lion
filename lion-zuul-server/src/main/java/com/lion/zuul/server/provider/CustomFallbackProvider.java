package com.lion.zuul.server.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lion.common.entity.Result;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * CustomFallbackProvider
 * 路由回退
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2019/01/02
 * Copyright 2019 Yanzheng. All rights reserved.
 */
@Component
public class CustomFallbackProvider implements FallbackProvider {
    /**
     * 返回值表示需要针对此微服务做回退处理（该名称一定要是注册中心 微服务中的那个 serviceId 名称）
     * 如果需要所有调用都支持回退，则return "*"或return null
     */
    @Override
    public String getRoute() {
        //return "lion-demo-consumer";
        //return "lion-demo-provider";
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            /**
             * 当路由服务出现宕机后，客户端再请求时候就会返回json提示
             */
            @Override
            public InputStream getBody() throws IOException {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(Result.failure(503, "调用失败，服务不可用！"));
                return new ByteArrayInputStream(jsonString.getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
