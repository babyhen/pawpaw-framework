package com.pawpaw.framework.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class PawpawQuickStartApplication extends PawpawAbstractApplication {


    @Override
    public ConfigurableApplicationContext run(Class[] sources, String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(sources);
        SpringApplication springApplication = builder.build();
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        return springApplication.run(args);
    }


}
