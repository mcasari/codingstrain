// Gatling: load-test the books endpoint and assert SLAs
public class BookSaveSimulation extends Simulation {

    {
        setUp(buildScenario()
            .injectOpen(rampUsersPerSec(1).to(100).during(ofSeconds(300)))
            .protocols(http.baseUrl("http://localhost:8080")))
        .assertions(
            global().responseTime().max().lte(10000),            // <= 10s
            global().successfulRequests().percent().gt(90.0));   // > 90% OK
    }

    ScenarioBuilder buildScenario() {
        return scenario("Load POST")
            .feed(randomTitles())                  // Faker-generated book titles
            .exec(http("create-book").post("/library/book")
                .header("Content-Type", "application/json")
                .body(StringBody("{ \"title\": \"${title}\" }")));
    }
}
