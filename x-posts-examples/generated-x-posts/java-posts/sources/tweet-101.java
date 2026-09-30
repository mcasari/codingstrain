// ❌ Eager — always built, even if never used
class EagerReports {
    private final HeavyAnalyzer analyzer = new HeavyAnalyzer();

    void maybeAnalyze(Report r) {
        if (r.needsDeepScan()) {
            analyzer.run(r);
        }
    }
}

// ✅ Lazy — create only on first need
class LazyReports {
    private HeavyAnalyzer analyzer;

    private HeavyAnalyzer analyzer() {
        if (analyzer == null) {
            analyzer = new HeavyAnalyzer();
        }
        return analyzer;
    }

    void maybeAnalyze(Report r) {
        if (r.needsDeepScan()) {
            analyzer().run(r);
        }
    }
}
