// ❌ Nulls serialized — noisy JSON for clients
class UserDto {
    String name = "Ada";
    String nickname = null; // still appears as "nickname": null
}

// ✅ Skip nulls on write
@JsonInclude(JsonInclude.Include.NON_NULL)
class UserDto {
    String name = "Ada";
    String nickname = null; // omitted from JSON entirely
}
// {"name":"Ada"}
