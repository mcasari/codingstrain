// ❌ Date / Calendar — mutable, easy to get wrong
Calendar cal = Calendar.getInstance();
cal.set(2026, 5, 31);                // looks like May — but month 5 is June!
cal.add(Calendar.DAY_OF_MONTH, 7);
Date deadline = cal.getTime();

SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
String text = fmt.format(deadline);    // SimpleDateFormat is not thread-safe

// ✅ java.time — immutable, readable, thread-safe
LocalDate today = LocalDate.of(2026, 5, 31);   // 5 = May, as you'd expect
LocalDate deadline = today.plusDays(7);

String text = deadline.format(DateTimeFormatter.ISO_LOCAL_DATE);
// "2026-06-07"

LocalDateTime meeting = LocalDateTime.of(2026, 5, 31, 14, 30);
ZonedDateTime rome = meeting.atZone(ZoneId.of("Europe/Rome"));
