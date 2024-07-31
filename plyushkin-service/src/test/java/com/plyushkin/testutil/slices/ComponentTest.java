package com.plyushkin.testutil.slices;

import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.extension.PostgresExtension;
import com.plyushkin.testutil.rest.TestControllers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Retention(RUNTIME)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestEntityManager
@Import({TestControllers.class, TestDbFacade.class})
@ExtendWith(PostgresExtension.class)
@ActiveProfiles("test")
public @interface ComponentTest {
}
