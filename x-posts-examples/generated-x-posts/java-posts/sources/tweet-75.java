public interface Notifier {
    void send(String message);

    // ✅ New method in v2 — default keeps old implementors compiling
    default void sendHtml(String html) {
        send(stripTags(html));   // fallback: treat as plain text
    }

    private static String stripTags(String html) {
        return html.replaceAll("<[^>]+>", "");
    }
}

// Existing class from v1 — still compiles, no changes required
public class SmsNotifier implements Notifier {
    public void send(String message) {
        smsGateway.deliver(message);
    }
    // sendHtml() inherited — SMS stays plain text
}

// New code can override when HTML is supported
public class EmailNotifier implements Notifier {
    public void send(String message) {
        mailClient.sendPlain(message);
    }

    @Override
    public void sendHtml(String html) {
        mailClient.sendHtml(html);
    }
}
