package com.plyushkin.testutil.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.env.Environment;

@TestComponent
public class TestControllers {
    @Autowired
    private Environment environment;
}
