package com.plyushkin.infra.security;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    @SneakyThrows
    public SecurityFilterChain httpBasicAuthFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) {
        return http.httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .securityMatchers(matchers -> matchers.requestMatchers("/api/**"))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .build();
    }
}
