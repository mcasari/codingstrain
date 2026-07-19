@Service
public class CheckoutService {

    @Observed(name = "checkout")
    @ObservationKeyValue(key = "cart.size",
                         value = "#{#cart.lines().size()}")
    public Order checkout(Cart cart) {
        return orderRepo.save(Order.from(cart));
    }
}

// Spans/metrics include cart.size=n for the observation
