package com.codingstrain.springcloud.sample.libraryapp.books.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@FeignClient(name = "author-service")
public interface AuthorClient {

    @GetMapping("/authors/author/{name}")
    public Optional<Author> findByName(@PathVariable("name") String name);

    @GetMapping("/authors/getInstance")
    @CircuitBreaker(name = "CircuitBreakerService")
    public String getInstance();

    @GetMapping("/authors/getInstanceLB")
    public String getInstanceLB();

}