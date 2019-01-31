package com.pawpaw.framework.core.config;

import com.pawpaw.framework.core.feign.PawpawFeignConfig;
import com.pawpaw.framework.core.swagger.Swagger2Config;
import com.pawpaw.framework.core.web.PawpawWebConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Import;

@Import({PawpawWebConfig.class, PawpawFeignConfig.class, Swagger2Config.class})
public class PawpawFrameworkAutoConfig {


}
