package com.plyushkin;

import com.plyushkin.testutil.slices.DBTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DBTest(properties = "spring.jpa.hibernate.ddl-auto=validate")
public class ValidateSchemaTest {

    @Test
    void sanity() {
        assertTrue(true);
    }
}
