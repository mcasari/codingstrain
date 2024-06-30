package com.codingstrain.springcloud.sample.libraryapp.books;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.http;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import com.github.javafaker.Faker;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.OpenInjectionStep.RampRate.RampRateOpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;


public class BookSaveSimulation extends Simulation {


    public BookSaveSimulation() {

        setUp(buildPostScenario()
            .injectOpen(injection())
            .protocols(setupProtocol())).assertions(global().responseTime()
          .max()
          .lte(10000), global().successfulRequests()
          .percent()
          .gt(90d));
    }

    private static ScenarioBuilder buildPostScenario() {
        return CoreDsl.scenario("Load POST Test")
            .feed(feedData())
            .exec(http("create-book").post("/library/book")
            .header("Content-Type", "application/json")
                .body(StringBody("{ \"title\": \"${title}\" }")));
    }


    private static Iterator<Map<String, Object>> feedData() {
        Faker faker = new Faker();
        Iterator<Map<String, Object>> iterator;
        iterator = Stream.generate(() -> {
              Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("title", faker.book()
                .title());
              return stringObjectMap;
          })
          .iterator();
        return iterator;
    }

    private static HttpProtocolBuilder setupProtocol() {
        return HttpDsl.http.baseUrl("http://localhost:8080")
          .acceptHeader("application/json")
          .maxConnectionsPerHost(10)
            .userAgentHeader("Performance Test");
    }

    private RampRateOpenInjectionStep injection() {
        int totalUsers = 100;
        double userRampUpPerInterval = 10;
        double rampUpIntervalInSeconds = 30;

        int rampUptimeSeconds = 300;
        int duration = 300;
        return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalInSeconds)).to(totalUsers)
            .during(Duration.ofSeconds(rampUptimeSeconds + duration));
    }
}
