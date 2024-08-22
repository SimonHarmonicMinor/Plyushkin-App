package com.plyushkin.expense;

import com.plyushkin.shared.LongSerdeProvider;
import com.plyushkin.shared.SerdeProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ExpenseSerdeProvidedConfig {
    @Bean
    SerdeProvider<ExpenseCategoryNumber> expenseNoteCategoryNumberSerdeProvider() {
        return new LongSerdeProvider<>() {
            @Override
            @SneakyThrows
            public ExpenseCategoryNumber asEntity(Long value) {
                return ExpenseCategoryNumber.create(value);
            }

            @Override
            public Long asNumber(ExpenseCategoryNumber value) {
                return value.getValue();
            }

            @Override
            public Class<ExpenseCategoryNumber> type() {
                return ExpenseCategoryNumber.class;
            }
        };
    }

    @Bean
    SerdeProvider<ExpenseNumber> expenseNumberSerdeProvider() {
        return new LongSerdeProvider<>() {
            @Override
            @SneakyThrows
            public ExpenseNumber asEntity(Long value) {
                return ExpenseNumber.create(value);
            }

            @Override
            public Long asNumber(ExpenseNumber value) {
                return value.getValue();
            }

            @Override
            public Class<ExpenseNumber> type() {
                return ExpenseNumber.class;
            }
        };
    }
}
