import net.ltgt.gradle.errorprone.errorprone
import java.nio.file.Paths;

plugins {
    java
    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.5"
    id("ru.vyarus.quality") version "5.0.0"
    id("net.ltgt.errorprone") version "4.0.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    id("org.openapi.generator") version "7.6.0"
}

group = "com.plyushkin"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["sentryVersion"] = "7.3.0"

dependencies {
    val lombokVersion = "1.18.32"
    errorprone("com.google.errorprone:error_prone_core:2.28.0")
    errorprone("com.uber.nullaway:nullaway:0.11.0")
    implementation("com.uber.nullaway:nullaway:0.11.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.cosium.spring.data:spring-data-jpa-entity-graph:3.2.2")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("io.sentry:sentry-spring-boot-starter-jakarta")
    implementation("org.flywaydb:flyway-core")
    implementation("com.h2database:h2")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("com.cosium.spring.data:spring-data-jpa-entity-graph-generator:3.2.2")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.4.8.Final")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("io.sentry:sentry-bom:${property("sentryVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.create("runStaticAnalysis") {
    dependsOn(
        "checkstyleMain",
        "pmdMain",
        "spotbugsMain",
        "checkstyleTest",
        "pmdTest",
        "spotbugsTest"
    )
}

quality {
    spotbugsLevel = "low"
}

tasks.withType<JavaCompile>().configureEach {
    // temporary solution until this issue is fixed
    // https://github.com/Cosium/spring-data-jpa-entity-graph/issues/193
    options.errorprone.isEnabled.set(false);
    options.errorprone.disableWarningsInGeneratedCode.set(true)
    options.encoding = "UTF-8"
    if (name == "compileTestJava") {
        options.errorprone.isEnabled.set(false)
    }
    if (!name.lowercase().contains("test")) {
        options.errorprone {
            check("NullAway", net.ltgt.gradle.errorprone.CheckSeverity.ERROR)
            option("NullAway:AnnotatedPackages", "com.plyushkin")
            option("NullAway:TreatGeneratedAsUnannotated", true)
            option("NullAway:ExcludedClassAnnotations", "lombok.NoArgsConstructor")
            option("NullAway:SuggestSuppressions", true)
        }
    }
}

openApi {
    customBootRun {
        args.set(listOf(
            "--spring.jpa.hibernate.ddl-auto=none",
            "--spring.flyway.enabled=false",
            "--spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false",
            "--spring.jpa.database=h2",
            "--spring.datasource.url=jdbc:h2:mem:testdb",
            "--spring.datasource.driverClassName=org.h2.Driver",
            "--spring.datasource.username=sa",
            "--spring.datasource.password=password",
            "--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
            "--default-users.enable-creation=false"
        ))
    }
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set(Paths.get("$buildDir", "openapi.json").toString())
    outputDir.set(Paths.get("$buildDir", "generated").toString())
    apiPackage.set("com.plyushkin.openapi.client")
    invokerPackage.set("com.plyushkin.openapi.client")
    modelPackage.set("com.plyushkin.openapi.client")
    library.set("native")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("openApiNullable", "false")
    configOptions.put("generateBuilders", "true")
    configOptions.put("serializationLibrary", "jackson")
    configOptions.put("useJakartaEe", "true")
}

sourceSets {
    test {
        java {
            srcDir(files("${openApiGenerate.outputDir.get()}/src/main"))
        }
    }
}

tasks.named("openApiGenerate") {
    dependsOn("generateOpenApiDocs")
}