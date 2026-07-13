// ❌ A new String object on every iteration
String csv = "";
for (String item : items) {
    csv += item + ",";
}

// ✅ One mutable buffer — no garbage
StringBuilder sb = new StringBuilder();
for (String item : items) {
    sb.append(item).append(',');
}
String csv = sb.toString();
