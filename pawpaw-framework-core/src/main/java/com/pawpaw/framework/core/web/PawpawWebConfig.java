package com.pawpaw.framework.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import com.pawpaw.framework.core.web.convertor.PawpawGlobalMessageConverter;
import com.pawpaw.framework.core.web.interceptor.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@ComponentScan("com.pawpaw.framework.core.web")
public class PawpawWebConfig implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        PawpawObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        converters.add(0, new PawpawGlobalMessageConverter(objectMapper));

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new PrintProcessTimeInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new PrintRequestDataInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new ThreadHandlerMapInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UrlCalledCounterInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new PrintFeignClientHeaderInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        PawpawObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        PawpawDefaultMessageHandlerExceptionResolver handler = new PawpawDefaultMessageHandlerExceptionResolver(objectMapper);
        resolvers.add(handler);
    }


}