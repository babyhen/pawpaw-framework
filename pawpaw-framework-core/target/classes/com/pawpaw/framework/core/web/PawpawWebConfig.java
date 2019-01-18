package com.pawpaw.framework.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired
    private ObjectMapper globalObjectMapper;


    @Bean
    public ObjectMapper buildGlobalObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        //配置om
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return om;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> notJacksonConverter = converters.stream().filter(e -> {
            return !(e instanceof MappingJackson2HttpMessageConverter);
        }).collect(Collectors.toList());
        converters.clear();
        converters.addAll(notJacksonConverter);
        converters.add(new PawpawGlobalMessageConverter(this.globalObjectMapper));

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PrintRequestDataInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        PawpawDefaultMessageHandlerExceptionResolver handler = new PawpawDefaultMessageHandlerExceptionResolver(this.globalObjectMapper);
        resolvers.add(handler);
    }


}