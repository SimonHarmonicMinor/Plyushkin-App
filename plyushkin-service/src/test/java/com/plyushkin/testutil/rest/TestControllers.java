package com.plyushkin.testutil.rest;

import com.plyushkin.infra.properties.DefaultUsersProperties;
import com.plyushkin.openapi.client.ApiClient;
import com.plyushkin.openapi.client.ExpenseCategoryControllerApi;
import com.plyushkin.openapi.client.ExpenseRecordControllerApi;
import com.plyushkin.openapi.client.WalletControllerApi;
import com.plyushkin.openapi.client.IncomeCategoryControllerApi;
import com.plyushkin.openapi.client.IncomeRecordControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.env.Environment;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@TestComponent
public class TestControllers {
    @Autowired
    private Environment environment;
    @Autowired
    private DefaultUsersProperties defaultUsersProperties;

    public ExpenseRecordControllerApi expenseRecordController() {
        return new ExpenseRecordControllerApi(newApiClient());
    }

    public ExpenseCategoryControllerApi expenseCategoryController() {
        return new ExpenseCategoryControllerApi(newApiClient());
    }

    public IncomeRecordControllerApi incomeRecordController() {
        return new IncomeRecordControllerApi(newApiClient());
    }

    public IncomeCategoryControllerApi incomeCategoryController() {
        return new IncomeCategoryControllerApi(newApiClient());
    }

    public WalletControllerApi walletController() {
        return new WalletControllerApi(newApiClient());
    }

    private ApiClient newApiClient() {
        final var user = defaultUsersProperties.values().entrySet().stream().findFirst().orElseThrow().getValue();
        final var apiClient = new ApiClient();
        apiClient.setScheme("http");
        apiClient.setHost("localhost");
        apiClient.setPort(environment.getProperty("local.server.port", int.class));
        apiClient.setRequestInterceptor(
                builder ->
                        builder.header(
                                "Authorization",
                                "Basic "
                                        + new String(
                                        Base64.getEncoder().encode(
                                                (user.username() + ":" + user.password())
                                                        .getBytes(UTF_8)
                                        ),
                                        UTF_8
                                )
                        )
        );
        return apiClient;
    }
}
