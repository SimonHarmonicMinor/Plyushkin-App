package com.plyushkin.wallet;

import com.plyushkin.shared.SerdeProvider;
import com.plyushkin.shared.StringSerdeProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WalletSerdeProviderConfig {
    @Bean
    SerdeProvider<WalletId> walletIdSerdeProvider() {
        return new StringSerdeProvider<>() {
            @Override
            @SneakyThrows
            public WalletId asEntity(String rawValue) {
                return WalletId.parse(rawValue);
            }

            @Override
            public String asString(WalletId value) {
                return value.getStringValue();
            }

            @Override
            public Class<WalletId> type() {
                return WalletId.class;
            }
        };
    }
}
