package com.plyushkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.plyushkin")
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@Modulithic(sharedModules = "infra")
public class PlyushkinServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlyushkinServiceApplication.class, args);
    }

}
