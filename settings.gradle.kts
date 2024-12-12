rootProject.name = "plyushkin-app"
include("plyushkin-web-ui", "plyushkin-backend")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { setUrl("https://maven.vaadin.com/vaadin-prereleases") }
        maven { setUrl("https://repo.spring.io/milestone") }
        maven { setUrl("https://maven.vaadin.com/vaadin-addons") }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { setUrl("https://maven.vaadin.com/vaadin-prereleases") }
        maven { setUrl("https://repo.spring.io/milestone") }
        maven { setUrl("https://maven.vaadin.com/vaadin-addons") }
    }
}