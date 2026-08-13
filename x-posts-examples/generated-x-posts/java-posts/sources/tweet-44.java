double n = 1234.56;

// ❌ Default JVM locale — may not match your users
DecimalFormat usStyle = new DecimalFormat("#,###.##");
// often: "1,234.56"

// ✅ Explicit symbols for the target locale
DecimalFormatSymbols it = new DecimalFormatSymbols(Locale.ITALY);
DecimalFormat italian = new DecimalFormat("#,###.##", it);
String text = italian.format(n); // "1.234,56"
