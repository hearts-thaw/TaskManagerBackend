package ru.itis.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
@Profile("!prod")
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        Collections.singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicate.not(PathSelectors.regex("/error")))
                .paths(Predicate.not(PathSelectors.regex("/profile")))
//                .paths(PathSelectors.any())
                .build();
    }
}
