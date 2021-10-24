package com.pawpaw.framework.web;

import com.pawpaw.framework.core.codec.ResultEncoder;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import com.pawpaw.framework.web.convertor.PawpawGlobalMessageConverter;
import com.pawpaw.framework.web.interceptor.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@EnableWebMvc
@ComponentScan("com.pawpaw.framework.web")
public class PawpawWebConfig implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        PawpawObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        ResultEncoder encoder = new ResultEncoder();
        converters.add(0, new PawpawGlobalMessageConverter(objectMapper, encoder));

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new PrintProcessTimeInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new PrintRequestDataInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new ThreadHandlerMapInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UrlCalledCounterInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        PawpawObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        ResultEncoder resultEncoder = new ResultEncoder();
        PawpawDefaultMessageHandlerExceptionResolver handler = new PawpawDefaultMessageHandlerExceptionResolver(objectMapper, resultEncoder);
        resolvers.add(handler);
    }

    //静态资源，走/static开头的路径，放在static目录下
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}