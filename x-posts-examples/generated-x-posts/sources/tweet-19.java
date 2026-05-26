@Service
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        kafkaTemplate.send("demo-topic", message);
    }
}
