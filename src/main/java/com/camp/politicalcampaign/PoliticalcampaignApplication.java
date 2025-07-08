package com.camp.politicalcampaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Clase principal para iniciar la aplicación Spring Boot.
 * Permite el despliegue como WAR en un contenedor servlet.
 */
@ServletComponentScan // Habilita el escaneo de servlets, filtros y listeners con anotaciones
@SpringBootApplication
public class PoliticalcampaignApplication extends SpringBootServletInitializer {

    /**
     * Configuración para despliegue en contenedor externo (WAR).
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PoliticalcampaignApplication.class);
    }

    /**
     * Método principal para ejecutar la aplicación como standalone.
     */
    public static void main(String[] args) {
        SpringApplication.run(PoliticalcampaignApplication.class, args);
    }
}

