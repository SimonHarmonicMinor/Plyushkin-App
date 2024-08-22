package com.plyushkin.income;

import com.plyushkin.shared.LongSerdeProvider;
import com.plyushkin.shared.SerdeProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class IncomeSerdeProviderConfig {
    @Bean
    SerdeProvider<IncomeNumber> incomeNumberSerdeProvider() {
        return new LongSerdeProvider<>() {
            @Override
            @SneakyThrows
            public IncomeNumber asEntity(Long value) {
                return IncomeNumber.create(value);
            }

            @Override
            public Long asNumber(IncomeNumber value) {
                return value.getValue();
            }

            @Override
            public Class<IncomeNumber> type() {
                return IncomeNumber.class;
            }
        };
    }

    @Bean
    SerdeProvider<IncomeCategoryNumber> incomeNoteCategoryNumberSerdeProvider() {
        return new LongSerdeProvider<>() {
            @Override
            @SneakyThrows
            public IncomeCategoryNumber asEntity(Long value) {
                return IncomeCategoryNumber.create(value);
            }

            @Override
            public Long asNumber(IncomeCategoryNumber value) {
                return value.getValue();
            }

            @Override
            public Class<IncomeCategoryNumber> type() {
                return IncomeCategoryNumber.class;
            }
        };
    }
}
