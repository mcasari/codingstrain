// ❌ Trust raw input — SQL injection, path traversal, bad data slip through
String sql = "SELECT * FROM users WHERE email = '" + email + "'";
Files.readString(Path.of("/data/" + filename));
int age = Integer.parseInt(request.getParameter("age"));

// ✅ Validate first, then use safe APIs
if (!EMAIL.matcher(email).matches()) {
    throw new BadRequestException("invalid email");
}
PreparedStatement ps = conn.prepareStatement(
    "SELECT * FROM users WHERE email = ?");
ps.setString(1, email);

Path base = Path.of("/data").toAbsolutePath().normalize();
Path file = base.resolve(filename).normalize();
if (!file.startsWith(base)) {
    throw new SecurityException("path escape blocked");
}

// ✅ Or declare rules on the DTO — fail fast at the boundary
public record SignupRequest(
    @NotBlank @Email String email,
    @Min(18) @Max(120) int age) {}

void signup(@Valid @RequestBody SignupRequest req) { ... }
