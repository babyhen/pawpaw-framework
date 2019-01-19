package com.pawpaw.framework.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.factory.ObjectMapperFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebMvc
@ComponentScan("com.pawpaw.framework.core.web")
public class PawpawWebConfig implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> notJacksonConverter = converters.stream().filter(e -> {
            return !(e instanceof MappingJackson2HttpMessageConverter);
        }).collect(Collectors.toList());
        converters.clear();
        converters.addAll(notJacksonConverter);
        ObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        converters.add(new PawpawGlobalMessageConverter(objectMapper));

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PrintRequestDataInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        ObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        PawpawDefaultMessageHandlerExceptionResolver handler = new PawpawDefaultMessageHandlerExceptionResolver(objectMapper);
        resolvers.add(handler);
    }


}