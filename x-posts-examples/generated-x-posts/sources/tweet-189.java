// Ignore unknown JSON fields instead of failing
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(String name, String email) {}
