import java.util.ArrayList;
import java.util.List;

// ❌ Inheritance — you get ArrayList's full API (add, remove, sort, …)
class LoggingList extends ArrayList<String> {
    @Override
    public boolean add(String item) {
        System.out.println("add: " + item);
        return super.add(item);   // coupled to ArrayList
    }
}

// ✅ Delegation — expose only what your class needs
class LoggingList {
    private final List<String> items = new ArrayList<>();

    public boolean add(String item) {
        System.out.println("add: " + item);
        return items.add(item);   // swap List impl anytime
    }

    public List<String> snapshot() {
        return List.copyOf(items);
    }
}
