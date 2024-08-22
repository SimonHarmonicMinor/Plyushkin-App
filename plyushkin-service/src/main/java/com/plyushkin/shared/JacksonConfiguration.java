package com.plyushkin.shared;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plyushkin.shared.serde.SerdeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JacksonConfiguration implements WebMvcConfigurer {
    private final List<SerdeProvider<?>> serdeProviders;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(customSerdeModule());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Module customSerdeModule() {
        final var module = new SimpleModule("Custom Serde module");
        for (final SerdeProvider serdeProvider : serdeProviders) {
            module.addSerializer(serdeProvider.type(), serdeProvider.serializer());
            module.addDeserializer(serdeProvider.type(), serdeProvider.deserializer());
        }
        return module;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        for (final SerdeProvider<?> serdeProvider : serdeProviders) {
            registry.addFormatterForFieldType(serdeProvider.type(), serdeProvider.formatter());
        }
    }
}
