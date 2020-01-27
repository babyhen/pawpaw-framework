package com.pawpaw.framework.core;

import com.pawpaw.framework.core.web.PawpawWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.config.EurekaClientConfigServerAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

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
