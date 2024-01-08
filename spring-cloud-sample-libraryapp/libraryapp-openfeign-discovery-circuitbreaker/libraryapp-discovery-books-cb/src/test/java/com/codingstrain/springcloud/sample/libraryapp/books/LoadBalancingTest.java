package com.codingstrain.springcloud.sample.libraryapp.books;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.mockito.ArgumentMatchers.startsWith;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import io.specto.hoverfly.junit.rule.HoverflyRule;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoadBalancingTest {

    private static Logger logger = LoggerFactory.getLogger(LoadBalancingTest.class);

    @RegisterExtension
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(dsl(

        service("account-service:8091").andDelay(
            10000, TimeUnit.MILLISECONDS)
            .forAll()
            .get(startsWith("/customer/"))
            .willReturn(success("account-service:8091", "application/json")),

        service("account-service:8092").andDelay(
                50, TimeUnit.MILLISECONDS)
                .forAll()
                .get(startsWith("/customer/"))
            .willReturn(success("account-service:8092", "application/json"))))

        .printSimulationData();

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCustomerWithAccounts() {
        int a = 0, b = 0;
        for (int i = 0; i < 1000; i++) {
            String result = restTemplate.getForObject("http://localhost:8080/library/getAuthorServiceInstance", String.class);
            logger.info("Result: ", result);
        }
        logger.info("TEST RESULT: 8091={}, 8092={}", a, b);
    }


}