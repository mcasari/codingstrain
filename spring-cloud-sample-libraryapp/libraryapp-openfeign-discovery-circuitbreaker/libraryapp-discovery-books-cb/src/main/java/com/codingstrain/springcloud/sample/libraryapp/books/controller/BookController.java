package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.books.dto.BookInfo;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/bookInfo", params = { "authorName", "bookTitle" })
    public BookInfo findBookInfoByTitle(@RequestParam("authorName") String authorName, @RequestParam("bookTitle") String bookTitle) {
        return bookService.findBookInfoByTitle(authorName, bookTitle);
    }

    @GetMapping(value = "/book/{title}")
    public Optional<Book> findByTitle(@PathVariable("title") String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping(value = "/getAuthorServiceInstance")
    @CircuitBreaker(name = "CircuitBreakerApi", fallbackMethod = "getAuthorServiceInstanceFallback")
    public String getAuthorServiceInstance() {
        return bookService.getAuthorServiceInstance();
    }

    @GetMapping(value = "/getAuthorServiceInstanceBulkhead")
    @Retry(name = "BulkheadApi", fallbackMethod = "getAuthorServiceInstanceBulkheadFallback")
    public String getAuthorServiceInstanceBulkhead() {
        return bookService.getAuthorServiceInstance();
    }

    public String getAuthorServiceInstanceFallback(Exception ex) {
        return "Fallback content";
    }

    @GetMapping(value = "/getAuthorServiceInstanceLB")
    public String getAuthorServiceInstanceLB() {
        return bookService.getAuthorServiceInstanceLB();
    }

    @GetMapping(value = "/getAuthorServiceInstanceRateLimiter")
    @Retry(name = "RateLimiterApi", fallbackMethod = "getAuthorServiceInstanceRateLimiterFallback")
    public String getAuthorServiceInstanceRateLimiter() {
        return bookService.getAuthorServiceInstance();
    }

    public String getAuthorServiceInstanceRateLimiterFallback(Exception ex) {
        return "Fallback content";
    }

    @GetMapping(value = "/getAuthorServiceInstanceRetry")
    @Retry(name = "RetryApi", fallbackMethod = "getAuthorServiceInstanceRetryFallback")
    public String getAuthorServiceInstanceRetry() {
        return bookService.getAuthorServiceInstance();
    }

    public String getAuthorServiceInstanceRetryFallback(Exception ex) {
        return "Fallback content";
    }

    @GetMapping(value = "/getAuthorServiceInstanceTimeLimiter")
    @Retry(name = "BulkheadApi", fallbackMethod = "getAuthorServiceInstanceTimeLimiterFallback")
    public String getAuthorServiceInstanceTimeLimiter() {
        return bookService.getAuthorServiceInstance();
    }

    public String getAuthorServiceInstanceTimeLimiterFallback(Exception ex) {
        return "Fallback content";
    }
}