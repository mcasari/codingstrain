// ❌ No null contract — callers guess, IDE stays quiet
String displayName(User user) {
    return user.getName().toUpperCase(); // NPE if user or name is null
}

// ✅ Nullness annotations — tooling can flag misuse
String displayName(@NonNull User user) {
    @Nullable String name = user.getName();
    if (name == null) {
        return "unknown";
    }
    return name.toUpperCase();
}
