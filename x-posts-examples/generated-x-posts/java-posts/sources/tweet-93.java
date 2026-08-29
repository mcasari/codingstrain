// ❌ Live reference — caller mutates your state
class BadTeam {
    private final List<String> members = new ArrayList<>();
    public List<String> getMembers() { return members; }
}
new BadTeam().getMembers().clear();

// ✅ Snapshot from getter
class GoodTeam {
    private final List<String> members = new ArrayList<>();
    public List<String> getMembers() { return List.copyOf(members); }
}

// ✅ DTO at the API boundary
record TeamDto(String name, List<String> members) {}
TeamDto dto = new TeamDto(team.getName(), team.getMembers());
