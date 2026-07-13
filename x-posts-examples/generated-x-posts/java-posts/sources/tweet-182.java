import java.util.List;
import java.util.stream.Stream;

// Stream.iterate(...) is infinite unless you stop it
List<Integer> firstFive = Stream.iterate(0, n -> n + 1)
    .limit(5)
    .toList();

System.out.println(firstFive); // [0, 1, 2, 3, 4]

// Same idea with generate(): sample 3 random UUIDs
List<String> samples = Stream.generate(() -> java.util.UUID.randomUUID().toString())
    .limit(3)
    .toList();
