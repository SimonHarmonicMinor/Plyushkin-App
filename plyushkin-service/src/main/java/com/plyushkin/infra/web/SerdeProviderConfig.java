package com.plyushkin.infra.web;

import com.plyushkin.budget.expense.ExpenseCategoryNumber;
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
                return value.getStringValue();
            }

            @Override
            public Class<WalletId> type() {
                return WalletId.class;
            }
        };
    }

    @Bean
    SerdeProvider<ExpenseCategoryNumber> expenseNoteCategoryNumberSerdeProvider() {
        return new LongSerdeProvider<>() {
            @Override
            @SneakyThrows
            public ExpenseCategoryNumber asEntity(long value) {
                return ExpenseCategoryNumber.create(value);
            }

            @Override
            public long asLong(ExpenseCategoryNumber value) {
                return value.getValue();
            }

            @Override
            public Class<ExpenseCategoryNumber> type() {
                return ExpenseCategoryNumber.class;
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
                return value.getStringValue();
            }

            @Override
            public Class<UserId> type() {
                return UserId.class;
            }
        };
    }
}
