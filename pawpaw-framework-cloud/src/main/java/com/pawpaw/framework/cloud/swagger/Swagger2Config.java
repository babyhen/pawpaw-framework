package com.pawpaw.framework.cloud.swagger;

// TODO 后续增加对swagger的设置   @EnableSwagger2
public class Swagger2Config {

/*
    @Autowired
    @Bean
    public Docket api(ServerProperties serverProperty, EurekaInstanceConfigBean instanceConfigBean) {
        String ip = instanceConfigBean.getIpAddress();
        int port = instanceConfigBean.getNonSecurePort();
        String swaggerIndex = "http://" + ip + ":" + port + "/swagger-ui.html";
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

    *
 */
}
