import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("com.github.node-gradle.node") version "7.0.2"
}

tasks.register<NpmTask>("npmTest") {
    args = listOf("test", "--", "--watchAll=false")
}

tasks.register<NpmTask>("npmBuild") {
    args = listOf("run", "build")
}

tasks.register("build") {
    dependsOn("npmInstall", "npmTest", "npmBuild")
}

tasks.register("test") {
    dependsOn("npmInstall", "npmTest")
}

