package com.pawpaw.framework.core.swagger;

import com.netflix.appinfo.EurekaInstanceConfig;
import com.pawpaw.framework.core.common.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@AutoConfigureAfter({EurekaClientAutoConfiguration.class, EmbeddedWebServerFactoryCustomizerAutoConfiguration.class})
@EnableSwagger2
public class Swagger2Config {


    @Autowired
    @Bean
    @ConditionalOnBean({EurekaInstanceConfig.class, ServerProperties.class})
    public Docket api(ServerProperties serverProperty, EurekaInstanceConfigBean instanceConfigBean) {

        String host = SystemUtil.getIPOrHostName();
        Integer port = serverProperty.getPort();
        if (port == null) {
            port = 8080;
        }
        String swaggerIndex = "http://" + host + ":" + port + "/swagger-ui.html";
        instanceConfigBean.setStatusPageUrl(swaggerIndex);

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pawpaw"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger-api文档")
                .description("swagger")
                .version("1.0")
                .build();
    }
}