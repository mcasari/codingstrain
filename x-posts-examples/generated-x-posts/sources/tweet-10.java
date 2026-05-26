@Valid
private Security security;

public static class Security {
    @NotBlank private String username;
    @NotBlank private String password;
}
