package com.jojodu.book.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * LoginUserArgumentResolver 가 스프링에서 인식될 수 있도록 함.
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    /**
     * HandlerMethodArgumentResolver 는 항상 WebMvcConfigurer 의
     * addArgumentResolvers()를 통해 추가해야 합니다. 다른 HandlerMethodArgumentResolver
     * 추가가 필요하다면 같은 방식으로 추가해줍니다.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
