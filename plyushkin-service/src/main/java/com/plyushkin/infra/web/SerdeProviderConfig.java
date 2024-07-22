package com.plyushkin.infra.web;

import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerdeProviderConfig {
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
                return value.getValue();
            }

            @Override
            public Class<WalletId> type() {
                return WalletId.class;
            }
        };
    }

    @Bean
    SerdeProvider<ExpenseNoteCategoryNumber> expenseNoteCategoryNumberSerdeProvider() {
        return new LongSerdeProvider<>() {
            @Override
            @SneakyThrows
            public ExpenseNoteCategoryNumber asEntity(long value) {
                return ExpenseNoteCategoryNumber.create(value);
            }

            @Override
            public long asLong(ExpenseNoteCategoryNumber value) {
                return value.getValue();
            }

            @Override
            public Class<ExpenseNoteCategoryNumber> type() {
                return ExpenseNoteCategoryNumber.class;
            }
        };
    }

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
                return value.getValue();
            }

            @Override
            public Class<UserId> type() {
                return UserId.class;
            }
        };
    }
}
