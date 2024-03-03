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

import com.codingstrain.springcloud.sample.libraryapp.books.ObsHandler;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;


@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/book/{title}")
    public Optional<Book> findByTitle(@PathVariable("title") String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping(value = "/getAuthor", params = { "authorName" })
    public Optional<Author> getAuthor(@RequestParam("authorName") String authorName) {
        ObservationRegistry registry = ObservationRegistry.create();
        registry.observationConfig()
            .observationHandler(new ObsHandler());
        ThreadLocal<Optional<Author>> thLocal = new ThreadLocal<Optional<Author>>();
        Observation.createNotStarted("my.observation", registry)
            .lowCardinalityKeyValue("authorType", "Poet")
            .highCardinalityKeyValue("authorName", authorName)
            .contextualName("getAuthorInfo")
            .observe(() -> {
                logger.info("request to the server");
                Optional<Author> response = bookService.getAuthor(authorName);
                thLocal.set(response);
                logger.info("response [{}]", response);
            });

        return thLocal.get();
    }

}