<!-- ❌ Wildcard exclusion — symptom gone, root cause hidden -->
<dependency>
  <groupId>com.example</groupId>
  <artifactId>legacy-client</artifactId>
  <exclusions>
    <exclusion>
      <groupId>*</groupId>
      <artifactId>*</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<!-- ✅ Exclude only the conflict — or manage the version -->
<exclusion>
  <groupId>commons-logging</groupId>
  <artifactId>commons-logging</artifactId>
</exclusion>
<!-- Better: let spring-boot-dependencies pick the version -->
