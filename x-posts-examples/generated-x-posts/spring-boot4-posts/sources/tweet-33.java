// settings.gradle.kts
plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

// build.gradle.kts
plugins {
  java
  id("org.springframework.boot") version "4.0.7"
  id("io.spring.dependency-management") version "1.1.7"
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}
