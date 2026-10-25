// ❌ Growing if/else — every new channel edits this method
void notify(String channel, String msg) {
    if (channel.equals("email")) sendEmail(msg);
    else if (channel.equals("sms")) sendSms(msg);
    else throw new IllegalArgumentException(channel);
}

// ✅ Strategy — same job, swappable behavior
interface Notifier {
    void send(String msg);
}
void notify(Notifier notifier, String msg) {
    notifier.send(msg); // EmailNotifier, SmsNotifier, …
}
