String nl = System.lineSeparator();

// ❌ Hardcoded \n — fine on Linux, awkward on Windows
String bad = "Date,Amount" + "\n" + "2026-01-01,42";

// ✅ OS-native newlines
String good = String.join(nl,
    "Date,Amount",
    "2026-01-01,42",
    "2026-01-02,17");

System.out.print(good);
// Windows → \r\n between lines · Linux/macOS → \n
