// ❌ Assumes US/UK style — fails or misreads European input
double bad = Double.parseDouble("1.234,56"); // NumberFormatException

// ✅ Locale-aware parsing (Italy: . thousands, , decimal)
NumberFormat it = NumberFormat.getInstance(Locale.ITALY);
Number value = it.parse("1.234,56"); // 1234.56

double n = value.doubleValue();
