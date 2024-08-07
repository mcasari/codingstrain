package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookService bookService;

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping(value = "/authorInfo", params = { "authorName" })
    public Optional<Author> getAuthor(@RequestParam("authorName") String authorName) {
        return bookService.getAuthor(authorName);
    }

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping(value = "/ping")
    public String ping() {
        return "fffff";
    }

}