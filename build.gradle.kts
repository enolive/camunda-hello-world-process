plugins {
  val kotlinVersion = "1.9.24"
  id("org.springframework.boot") version "3.3.0"
  id("io.spring.dependency-management") version "1.1.5"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
}

group = "de.welcz"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  val camundaVersion = "7.21.0"
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.postgresql:postgresql")
  implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:$camundaVersion")
  implementation("io.github.oshai:kotlin-logging:5.1.0")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.camunda.bpm:camunda-bpm-junit5:$camundaVersion")
  testImplementation("org.camunda.bpm:camunda-bpm-assert:$camundaVersion")
  testImplementation("io.kotest:kotest-assertions-core:5.9.1")
  testImplementation("io.mockk:mockk:1.13.16")
  testImplementation("org.camunda.community.process_test_coverage:camunda-process-test-coverage-junit5-platform-7:2.7.0")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testRuntimeOnly("com.h2database:h2")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
