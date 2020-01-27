package com.pawpaw.framework.core;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class PawpawAbstractApplication {


    public ConfigurableApplicationContext run(Class... sources) {
        return this.run(sources, new String[0]);

    }


    public abstract ConfigurableApplicationContext run(Class[] sources, String[] args);
}
