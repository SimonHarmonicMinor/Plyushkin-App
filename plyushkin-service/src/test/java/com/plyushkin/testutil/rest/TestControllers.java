package com.plyushkin.testutil.rest;

import com.plyushkin.openapi.client.ApiClient;
import com.plyushkin.openapi.client.ExpenseCategoryControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.env.Environment;

@TestComponent
public class TestControllers {
    @Autowired
    private Environment environment;

    public ExpenseCategoryControllerApi expenseCategoryController() {
        return new ExpenseCategoryControllerApi(newApiClient());
    }

    private ApiClient newApiClient() {
        final var apiClient = new ApiClient();
        apiClient.setScheme("http");
        apiClient.setHost("localhost");
        apiClient.setPort(environment.getProperty("local.server.port", int.class));
        return apiClient;
    }
}
