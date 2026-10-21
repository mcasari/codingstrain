// ✅ Producer — send with KafkaTemplate
@Service
class MessageProducer {
    private final KafkaTemplate<String, String> kafka;

    MessageProducer(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    void send(String message) {
        kafka.send("demo-topic", message);
    }
}

// ✅ Consumer — method becomes a listener
@Service
class MessageConsumer {
    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    void listen(String message) {
        System.out.println("Received: " + message);
    }
}
