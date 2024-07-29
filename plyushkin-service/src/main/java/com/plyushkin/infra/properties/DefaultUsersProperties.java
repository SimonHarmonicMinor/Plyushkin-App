package com.plyushkin.infra.properties;

import com.plyushkin.user.UserId;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

@ConfigurationProperties("default-users")
public record DefaultUsersProperties(
        boolean enableCreation,
        @NestedConfigurationProperty
        Map<UserId, Info> values
) {

    public record Info(String username, String password) {
    }
}
