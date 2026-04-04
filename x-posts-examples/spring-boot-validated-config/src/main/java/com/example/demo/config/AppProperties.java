package com.example.demo.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @NotBlank
    private String name;

    @Min(1)
    @Max(60)
    private Integer timeout;

    @Valid
    private Security security;

    @NotEmpty
    private List<@NotBlank String> servers;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getTimeout() { return timeout; }
    public void setTimeout(Integer timeout) { this.timeout = timeout; }

    public Security getSecurity() { return security; }
    public void setSecurity(Security security) { this.security = security; }

    public List<String> getServers() { return servers; }
    public void setServers(List<String> servers) { this.servers = servers; }

    public static class Security {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
