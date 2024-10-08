package com.plyushkin.shared.converters;

import com.plyushkin.shared.UserId;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
class UserIdConverter implements Converter<String, UserId> {
    @Override
    @SneakyThrows
    public UserId convert(String source) {
        return UserId.parse(source);
    }
}
