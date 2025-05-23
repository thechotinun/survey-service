package com.survey.v1.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {
        @Server(url = "https://survey-service-dev.lab-359.work/api", description = "Development Server"),
        @Server(url = "http://localhost:3100/api", description = "Local Server")
})
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Docs API")
                        .description("API Documentation for Survey Service")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Chotinun")
                                .email("thechotinun@gmail.com")));
    }
}