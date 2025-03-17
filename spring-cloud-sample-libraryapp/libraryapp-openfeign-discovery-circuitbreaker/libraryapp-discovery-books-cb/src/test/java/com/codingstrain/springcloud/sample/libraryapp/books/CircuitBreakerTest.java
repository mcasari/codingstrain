package com.codingstrain.springcloud.sample.libraryapp.books;

import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CircuitBreakerTest {

    @RegisterExtension
    static WireMockExtension mockService = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig()
            .port(8091))
        .build();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Test
    public void testCircuitBreaker() {
        mockService.stubFor(WireMock.get("/authors/getInstance")
            .willReturn(serverError()));

        Optional<CircuitBreaker> circuitBreakerOptional = circuitBreakerRegistry.getAllCircuitBreakers()
            .stream()
            .findFirst();
        CircuitBreaker circuitBreaker = circuitBreakerOptional.get();

        for (int i = 0; i < 5; i++) {
            restTemplate.getForEntity("http://localhost:8080/library/getAuthorServiceInstance", String.class);
            if (i < 4) {
                assertTrue(circuitBreaker.getState()
                    .equals(CircuitBreaker.State.CLOSED));
            } else {
                assertTrue(circuitBreaker.getState()
                    .equals(CircuitBreaker.State.OPEN));
            }
        }

        for (int i = 0; i < 5; i++) {
            restTemplate.getForEntity("http://localhost:8080/library/getAuthorServiceInstance", String.class);
            assertTrue(circuitBreaker.getState()
                .equals(CircuitBreaker.State.OPEN) || circuitBreaker.getState()
                    .equals(CircuitBreaker.State.HALF_OPEN));
        }

    }

}