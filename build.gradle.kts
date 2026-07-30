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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-resttestclient")
    testImplementation("org.apache.groovy:groovy")
    testImplementation("org.apache.groovy:groovy-json")
    testImplementation("org.spockframework:spock-core:2.4-groovy-5.0")
    testImplementation("org.spockframework:spock-spring:2.4-groovy-5.0")
}

tasks.test {
    useJUnitPlatform()
}
