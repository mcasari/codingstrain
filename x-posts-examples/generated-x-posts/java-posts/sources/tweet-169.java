List<Integer> scores = List.of(90, 75, 88, 92, 70);

// ❌ Several passes — count, sum, min, max, average separately
long count = scores.stream().count();
int sum = scores.stream().mapToInt(i -> i).sum();
int min = scores.stream().mapToInt(i -> i).min().orElseThrow();
int max = scores.stream().mapToInt(i -> i).max().orElseThrow();
double avg = scores.stream().mapToInt(i -> i).average().orElseThrow();

// ✅ One pass — all stats together
IntSummaryStatistics stats = scores.stream()
    .collect(Collectors.summarizingInt(i -> i));

stats.getCount();    // 5
stats.getSum();      // 415
stats.getMin();      // 70
stats.getMax();      // 92
stats.getAverage();  // 83.0
