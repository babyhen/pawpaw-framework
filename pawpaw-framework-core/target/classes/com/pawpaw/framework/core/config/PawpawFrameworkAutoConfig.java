package com.pawpaw.framework.core.config;

import com.pawpaw.framework.core.feign.PawpawFeignConfig;
import com.pawpaw.framework.core.web.PawpawWebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PawpawFrameworkConfigProperty.class,PawpawWebConfig.class, PawpawFeignConfig.class})
public class PawpawFrameworkAutoConfig {


}
