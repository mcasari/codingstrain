public record SignupRequest(
        @NotBlank String name,
        @Email String email) {}

@PostMapping("/signup")
public void signup(@Valid @RequestBody SignupRequest req) {
    service.register(req);
}
