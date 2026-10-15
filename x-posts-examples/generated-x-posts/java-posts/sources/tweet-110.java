List<String> items = List.of("a", "b", "c");

// ❌ New String on every iteration
String csv = "";
for (String item : items) {
    csv += item + ",";
}

// ✅ One mutable buffer — then toString once
StringBuilder sb = new StringBuilder();
for (String item : items) {
    sb.append(item).append(',');
}
String csv = sb.toString();
