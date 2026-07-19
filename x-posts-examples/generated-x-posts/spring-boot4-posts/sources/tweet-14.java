@Service
public class ShippingNotifier {

    private final JmsClient jms;

    public ShippingNotifier(JmsClient jms) {
        this.jms = jms;
    }

    public void shipped(Order order) {
        jms.destination("shipping.events")
           .send(order);
    }

    public Order receive() {
        return jms.destination("shipping.events")
                  .receive(Order.class);
    }
}
