@Service
public class MessageProducer {
  public void send(String message) {
    kafkaTemplate.send("demo-topic", message);
  }
}
