package com.pawpaw.framework.cloud;

import com.pawpaw.framework.cloud.feign.PawpawFeignConfig;
import com.pawpaw.framework.core.PawpawAbstractApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class PawpawSpringCloudApplication extends PawpawAbstractApplication {


    @Override
    public ConfigurableApplicationContext run(Class[] sources, String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(sources);
        builder.sources(PawpawFeignConfig.class);
        return builder.run(args);
    }


}
