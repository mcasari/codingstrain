package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;

@RestController
@RequestMapping("/library")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/book", params = { "title" })
    public Optional<Book> findByTitle(@RequestParam("title") String title) {
        return bookService.findByTitle(title);
    }

}