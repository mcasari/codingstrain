<!-- try a milestone without custom repositories -->
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.1.0-M2</version>
</parent>

// Gradle: mavenCentral() is enough for Boot 4 milestones
repositories {
  mavenCentral()
}
