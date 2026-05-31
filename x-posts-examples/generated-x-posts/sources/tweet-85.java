// ❌ Return the internal list — callers can mutate your state
public class Team {
    private final List<String> members = new ArrayList<>();

    public List<String> getMembers() {
        return members;          // caller: getMembers().clear() wipes the team
    }

    public void add(String name) { members.add(name); }
}

// ✅ Return an unmodifiable view — internal state stays yours
public class Team {
    private final List<String> members = new ArrayList<>();

    public List<String> getMembers() {
        return Collections.unmodifiableList(members);
        // or: return List.copyOf(members);   // snapshot, safe if list changes later
    }

    public void add(String name) { members.add(name); }
}

// Bonus: you can switch ArrayList → Set internally without breaking callers
