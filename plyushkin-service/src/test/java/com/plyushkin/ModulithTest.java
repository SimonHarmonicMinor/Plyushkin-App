package com.plyushkin;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModulithTest {
    private final ApplicationModules modules = ApplicationModules.of(PlyushkinServiceApplication.class);

    @Test
    void verifyPackageConformity() {
        modules.verify();
    }

    @Test
    void createModulithsDocumentation() {
        new Documenter(modules).writeDocumentation();
    }
}
