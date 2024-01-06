package com.codingstrain.springcloud.sample.libraryapp.books;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksControllerTest {

    @RegisterExtension
    static WireMockExtension mockService = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig()
            .port(8091))
        .build();

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCircuitBreaker() {
        mockService.stubFor(WireMock.get("/library/author/Goethe")
            .willReturn(serverError()));

        for (int i = 0; i < 5; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity("/library/authorInfo", String.class, "Goethe");
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        for (int i = 0; i < 5; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity("/library/authorInfo", String.class, "Goethe");
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        }

        mockService.verify(5, getRequestedFor(urlEqualTo("/library/authorInfo")));
    }

}