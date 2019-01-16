package com.pawpaw.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.pawpaw.framework.web.PawpawWebConfig;
@Configuration
@Import(PawpawWebConfig.class)
public class PawpawFrameworkAutoConfig {
}
