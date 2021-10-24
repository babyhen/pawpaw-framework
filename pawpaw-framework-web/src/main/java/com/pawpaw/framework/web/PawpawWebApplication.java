package com.pawpaw.framework.web;

import com.pawpaw.framework.core.PawpawAbstractApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public  class PawpawWebApplication extends  PawpawAbstractApplication {


    @Override
    public ConfigurableApplicationContext run(Class[] sources, String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(sources);
        //pawpaw框架的mvc的配置
        builder.sources(PawpawWebConfig.class);
        SpringApplication springApplication = builder.build();
        return springApplication.run(args);
    }


}
