package com.example.taller.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan("com.example.taller.controller") // Solo escanear controladores
                .build();
    }

    @Bean
    public GroupedOpenApi vehiculoApi() {
        return GroupedOpenApi.builder()
                .group("Vehiculo API")
                .pathsToMatch("/api/vehiculos/**") // Solo incluir las rutas de Vehiculo
                .build();
    }

    @Bean
    public GroupedOpenApi propietarioApi() {
        return GroupedOpenApi.builder()
                .group("Propietario API")
                .pathsToMatch("/api/propietarios/**") // Solo incluir las rutas de Propietario
                .build();
    }

    @Bean
    public GroupedOpenApi ordenTrabajoApi() {
        return GroupedOpenApi.builder()
                .group("Orden Trabajo API")
                .pathsToMatch("/api/ordenes/**") // Solo incluir las rutas de Orden Trabajo
                .build();
    }

}

