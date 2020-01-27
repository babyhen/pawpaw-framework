package com.pawpaw.framework.core.config;

import com.pawpaw.framework.core.ali.AliConfig;
import com.pawpaw.framework.core.feign.PawpawFeignConfig;
import com.pawpaw.framework.core.swagger.Swagger2Config;
import com.pawpaw.framework.core.web.PawpawWebConfig;
import org.springframework.context.annotation.Import;

@Import({PawpawWebConfig.class, PawpawFeignConfig.class, Swagger2Config.class, AliConfig.class})
public class PawpawFrameworkAutoConfig {


}
