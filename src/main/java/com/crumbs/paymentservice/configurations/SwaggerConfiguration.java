package com.crumbs.paymentservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/payment-service/**"))
                .apis(RequestHandlerSelectors.basePackage("com.crumbs"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Payment Service API",
                "API used for processing payments in Crumbs Food Service",
                "1.0",
                "",
                new Contact("Crumbs Application", "https://crumbs-ss.link", "crumbsFoodService@gmail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }


}
