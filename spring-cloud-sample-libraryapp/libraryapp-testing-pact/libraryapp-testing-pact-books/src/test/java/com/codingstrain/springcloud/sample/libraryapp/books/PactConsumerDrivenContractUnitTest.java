package com.codingstrain.springcloud.sample.libraryapp.books;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestTemplate;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

@ExtendWith(PactConsumerTestExt.class)
public class PactConsumerDrivenContractUnitTest {

    @Pact(consumer = "ConsumerService", provider = "ProviderService")
    public V4Pact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
          .given("test GET")
            .uponReceiving("GET REQUEST")
            .path("/authors/findAuthor")
            .query("name=Goethe")
            .method("GET")
          .willRespondWith()
            .status(200)
            .headers(headers)
            .body("{\"name\":\"Goethe\",\"biography\":\"Bio info\"}")
            .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createPact")
    void runTest(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(mockServer.getUrl() + "/authors/findAuthor?name=Goethe", String.class);
        assertEquals("{\"name\":\"Goethe\",\"biography\":\"Bio info\"}", response);

    }

}