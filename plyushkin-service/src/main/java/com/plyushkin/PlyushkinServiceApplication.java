package com.plyushkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.plyushkin")
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class PlyushkinServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlyushkinServiceApplication.class, args);
    }

}
