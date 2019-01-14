package com.pawpaw.framework.config;

import com.pawpaw.framework.config.web.PawpawWebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PawpawWebConfig.class)
public class PawpawFrameworkAutoConfig {
}
