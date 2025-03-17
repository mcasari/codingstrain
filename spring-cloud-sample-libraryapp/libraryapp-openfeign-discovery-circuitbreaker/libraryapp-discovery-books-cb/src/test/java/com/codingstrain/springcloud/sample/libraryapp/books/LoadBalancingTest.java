package com.codingstrain.springcloud.sample.libraryapp.books;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.startsWith;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit5.HoverflyExtension;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(HoverflyExtension.class)
class LoadBalancingTest {

    private static Logger logger = LoggerFactory.getLogger(LoadBalancingTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLoadBalancing(Hoverfly hoverfly) {
        hoverfly.simulate(dsl(
            service("http://author-service:8091").andDelay(10000, TimeUnit.MILLISECONDS)
                .forAll()
                .get(startsWith("/authors/getInstanceLB"))
                .willReturn(success("author-service:8091", "application/json")),
    
            service("http://author-service:8092").andDelay(50, TimeUnit.MILLISECONDS)
                .forAll()
                .get(startsWith("/authors/getInstanceLB"))
                .willReturn(success("author-service:8092", "application/json"))));

        int a = 0, b = 0;
        for (int i = 0; i < 10; i++) {
            String result = restTemplate.getForObject("http://localhost:8080/library/getAuthorServiceInstanceLB", String.class);
            if (result.contains("8091")) {
                ++a;
            } else if (result.contains("8092")) {
                ++b;
            }
            logger.info("Result: ", result);
        }
        logger.info("Result: author-service:8091={}, author-service:8092={}", a, b);
    }

}