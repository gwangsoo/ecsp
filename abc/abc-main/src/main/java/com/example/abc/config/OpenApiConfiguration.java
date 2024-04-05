package com.example.abc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Care-Easy APIs",
                description = "XR-Friends Care-Easy API",
                version = "v1"
        ),
        servers = {@Server(url = "/services/backend/", description = "Default Server URL")}
//        servers = {@Server(url = "http://127.0.0.1:8088/services/backend"), @Server(url = "http://localhost:8088/services/backend"), @Server(url = "http://localhost:8080/services/backend"), @Server(url = "https://careeasy.xr-friends.com/services/backend")}
)
@Configuration
public class OpenApiConfiguration {
}
