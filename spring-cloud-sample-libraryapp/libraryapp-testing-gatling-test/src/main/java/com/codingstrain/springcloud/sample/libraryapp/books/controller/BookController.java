package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @PostMapping(value = "/book", consumes = "application/json", produces = "application/json")
    public Book createPerson(@RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping(value = "/findBookByTitle", params = { "title" })
    public Optional<Book> findByTitle(@RequestParam String title) {
        return bookService.findByTitle(title);
    }


}