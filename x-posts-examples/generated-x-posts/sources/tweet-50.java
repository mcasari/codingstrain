// 1) Eureka Server — service registry
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApp {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApp.class, args);
    }
}

// 2) Microservice — registers as "books-service"
// application.yml → spring.application.name: books-service

@FeignClient(name = "author-service")
public interface AuthorClient {
    @GetMapping("/authors/{id}")
    Author getAuthor(@PathVariable String id);
}
