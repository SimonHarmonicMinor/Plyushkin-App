package com.plyushkin.user;

import com.plyushkin.shared.SerdeProvider;
import com.plyushkin.shared.StringSerdeProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserSerdeProviderConfig {
    @Bean
    SerdeProvider<UserId> userIdSerdeProvider() {
        return new StringSerdeProvider<>() {
            @Override
            @SneakyThrows
            public UserId asEntity(String rawValue) {
                return UserId.parse(rawValue);
            }

            @Override
            public String asString(UserId value) {
                return value.getStringValue();
            }

            @Override
            public Class<UserId> type() {
                return UserId.class;
            }
        };
    }
}
