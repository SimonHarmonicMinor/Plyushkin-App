package com.plyushkin.testutil.slices;

import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.extension.PostgresExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestDbFacade.class})
@ExtendWith(PostgresExtension.class)
@ActiveProfiles("test")
public @interface DBTest {
    @AliasFor(annotation = DataJpaTest.class, attribute = "properties")
    String[] properties() default {};
}
