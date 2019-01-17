package com.pawpaw.framework.core;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class PawpawApplication {


    public ConfigurableApplicationContext run(Class bootClass) {
      return this.run(bootClass,new String[0]);

    }
    public ConfigurableApplicationContext run(Class bootClass, String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(bootClass).run(args);
        return applicationContext;

    }

}
