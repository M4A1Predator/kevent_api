package com.gamitology.kevent.kevent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Configuration
@EnableSwagger2
//@Profile({})
public class SwaggerConfig {
    @Value("${SWAGGER_HOST:}")
    private String host;

    @Value("${SWAGGER_PROTOCALS:http}")
    private Set<String> protocols;

    @Bean
    public Docket api(ServletContext servletContext) {
        String headerType = "header";

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .protocols(protocols)
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return "/";
                    }
                })
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.gz.inventory.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(
                        new ApiKey("access_token", "access_token", headerType),
                        new ApiKey("refresh_token", "refresh_token", headerType)
                ));

        if (host != null && !host.isEmpty()) {
            docket.host(host);
        }

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "KEVENT API",
                "Kevent API",
                "0.0.1",
                "Terms of service",
                new Contact("admin", "www.nckpop.com", ""),
                "License of API", "API license URLS", Collections.emptyList());
    }
}
