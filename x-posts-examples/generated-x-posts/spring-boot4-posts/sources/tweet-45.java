// build.gradle.kts
tasks.named<BootJar>("bootJar") {
  manifest {
    attributes(
      "Spring-Boot-Jar-Type" to "development-tool"
    )
  }
}

// Or MANIFEST.MF:
// Spring-Boot-Jar-Type: development-tool

// Boot’s packaging excludes these from nested app jars
