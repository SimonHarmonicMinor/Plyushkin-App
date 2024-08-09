package com.plyushkin.infra.web;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.ExpenseNumber;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import com.plyushkin.budget.income.IncomeNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

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
