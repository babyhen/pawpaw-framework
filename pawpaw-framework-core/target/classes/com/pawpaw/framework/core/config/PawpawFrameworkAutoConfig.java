package com.pawpaw.framework.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.pawpaw.framework.core.web.PawpawWebConfig;
@Configuration
@Import(PawpawWebConfig.class)
public class PawpawFrameworkAutoConfig {
}
