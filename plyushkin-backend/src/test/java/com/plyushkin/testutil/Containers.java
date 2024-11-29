package com.plyushkin.testutil;

import org.testcontainers.containers.PostgreSQLContainer;

public class Containers {
    public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13.5");

    private Containers() {
        // no op
    }
}
