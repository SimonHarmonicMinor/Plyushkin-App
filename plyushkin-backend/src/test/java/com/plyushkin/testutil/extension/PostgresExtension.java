package com.plyushkin.testutil.extension;

import com.plyushkin.testutil.Containers;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.lifecycle.Startables;

public class PostgresExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        final var container = Containers.POSTGRES;
        if (!container.isRunning()) {
            Startables.deepStart(container).join();
            System.setProperty("spring.datasource.url", container.getJdbcUrl());
            System.setProperty("spring.datasource.username", container.getUsername());
            System.setProperty("spring.datasource.password", container.getPassword());
        }
    }
}
