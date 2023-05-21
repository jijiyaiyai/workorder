package com.fr.workorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket Docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfoBuilder()
                .contact(new Contact("Franky", "", ""))
                .title("运维工单管理系统-开发文档")
                .description("运维工单管理系统-开发文档")
                .build();

        docket.apiInfo(apiInfo);

        docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.fr.workorder.control"));

        return docket;
    }
}
