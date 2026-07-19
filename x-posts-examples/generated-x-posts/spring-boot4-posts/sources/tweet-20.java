// build.gradle.kts
dependencies {
  implementation("org.springframework.boot:spring-boot-starter-kotlinx-serialization-json")
}

@Serializable
data class Book(val id: Long, val title: String)

# application.yml
spring:
  kotlinx:
    serialization:
      json:
        pretty-print: true
        ignore-unknown-keys: true

// Json HTTP message converter is registered ahead of fallbacks
