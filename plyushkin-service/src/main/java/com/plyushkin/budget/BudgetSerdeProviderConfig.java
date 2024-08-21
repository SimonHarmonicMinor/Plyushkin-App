package com.plyushkin.budget;

import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
import com.plyushkin.infra.DoubleSerdeProvider;
import com.plyushkin.infra.SerdeProvider;
import com.plyushkin.infra.StringSerdeProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
class BudgetSerdeProviderConfig {

    @Bean
    SerdeProvider<Currency> currencySerdeProvider() {
        return new StringSerdeProvider<>() {
            @Override
            public Currency asEntity(String rawValue) {
                return Currency.of(rawValue);
            }

            @Override
            public String asString(Currency value) {
                return value.value();
            }

            @Override
            public Class<Currency> type() {
                return Currency.class;
            }
        };
    }

    @Bean
    SerdeProvider<Money> moneySerdeProvider() {
        return new DoubleSerdeProvider<>() {
            @Override
            public Class<Money> type() {
                return Money.class;
            }

            @Override
            @SneakyThrows
            public Money asEntity(Double value) {
                return Money.create(BigDecimal.valueOf(value));
            }

            @Override
            public Double asNumber(Money value) {
                return value.getValue().doubleValue();
            }
        };
    }
}
