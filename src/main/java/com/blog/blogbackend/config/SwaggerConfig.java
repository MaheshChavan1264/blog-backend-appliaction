package com.blog.blogbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope scopes = new AuthorizationScope("global", "accessEverything");
        return Collections.singletonList(new SecurityReference("JWT", new AuthorizationScope[]{scopes}));
    }

    @Bean
    public Docket api() {


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Collections.singletonList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getInfo() {

        return new ApiInfo("Blogging Application: Backend Part",
                "This project is developed by Mahesh Chavan",
                "1.0",
                "Terms of Service",
                new Contact("Mahesh", "https://react-portfolio-delta-ruby.vercel.app/", "mahesh.chavan1264@gmail.com"),
                "License of APIs", "API License Url", Collections.emptyList()
        );
    }
}
