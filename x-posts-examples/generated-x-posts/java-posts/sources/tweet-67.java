// ⚠️ Compiles fine — warnings (if enabled) flag the real problems:
List list = new ArrayList();          // [rawtypes] raw type
list.add("hello");
Integer n = (Integer) list.get(0);    // [unchecked] unsafe cast → ClassCastException at runtime

// ✅ Fix the code — generics remove the cast, so the bug can't happen
List<String> safe = new ArrayList<>();
safe.add("hello");
String s = safe.get(0);               // type-safe, no cast needed

// --- Make warnings fail the build so the ⚠️ block never ships ---

// Maven (pom.xml)
// <plugin>
//   <artifactId>maven-compiler-plugin</artifactId>
//   <configuration>
//     <compilerArgs>
//       <arg>-Xlint:all</arg>
//       <arg>-Werror</arg>
//     </compilerArgs>
//   </configuration>
// </plugin>

// javac:  javac -Xlint:all -Werror *.java
