String name = "Ada";
int score = 98;

// ❌ Concatenation — hard to read as it grows
String msg = "Player " + name + " scored " + score + " points";

// ✅ String.format — clear template + arguments
String msg = String.format(
    "Player %s scored %d points", name, score);

// ✅ MessageFormat — locale-friendly patterns
String i18n = MessageFormat.format(
    "Player {0} scored {1} points", name, score);
