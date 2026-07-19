// ❌ Boot 3 Gradle
// tasks.named<BootJar>("bootJar") {
//   launchScript()
// }

// ✅ Boot 4 — still create a fat jar, run with java
tasks.named<BootJar>("bootJar") {
  archiveFileName.set("orders-service.jar")
}

// Deploy:
// java -jar orders-service.jar
// or: ./gradlew bootBuildImage && docker run …
