package com.pawpaw.framework.quickstart;

import com.pawpaw.framework.core.PawpawAbstractApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class PawpawQuickStartApplication extends PawpawAbstractApplication {


    @Override
    public ConfigurableApplicationContext run(Class[] sources, String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(sources);
        return builder.run(args);
    }


}
