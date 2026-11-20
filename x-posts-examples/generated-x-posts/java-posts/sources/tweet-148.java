// ❌ trim() — only chars <= U+0020; NBSP stays
String a = "\u00A0hello\u00A0".trim();
// still "\u00A0hello\u00A0"

// ✅ strip() — Unicode whitespace (Java 11+)
String b = "\u00A0hello\u00A0".strip();
// "hello"
