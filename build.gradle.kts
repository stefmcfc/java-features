plugins {
    java
    groovy
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "uk.co.stefirby.java.features"
version = "0.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

// Structured concurrency (JEP 453) is a preview API on JDK 21. Only this
// source set compiles with --enable-preview, keeping the flag's blast radius
// to the one class that needs it instead of enabling preview project-wide.
val preview: SourceSet by sourceSets.creating {
    java.srcDir("src/preview/java")
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
}

configurations[preview.implementationConfigurationName]
    .extendsFrom(configurations.implementation.get())

tasks.named<JavaCompile>("compilePreviewJava") {
    options.compilerArgs.add("--enable-preview")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-resttestclient")
    testImplementation("org.apache.groovy:groovy")
    testImplementation("org.apache.groovy:groovy-json")
    testImplementation("org.spockframework:spock-core:2.4-groovy-5.0")
    testImplementation("org.spockframework:spock-spring:2.4-groovy-5.0")
    testImplementation(preview.output)
}

// Preview classfiles (minor version 0xFFFF) only load in a JVM started with
// --enable-preview, so the test JVM and the forked Groovy test compiler need
// the runtime flag; no other compilation sees preview features.
tasks.named<GroovyCompile>("compileTestGroovy") {
    groovyOptions.forkOptions.jvmArgs = (groovyOptions.forkOptions.jvmArgs ?: mutableListOf())
        .plus("--enable-preview")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}
