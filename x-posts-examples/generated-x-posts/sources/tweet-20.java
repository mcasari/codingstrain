@KafkaListener(topics = "demo-topic", groupId = "demo-group")
public void listen(String message) {
  System.out.println("Received: " + message);
}
