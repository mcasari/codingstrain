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

import com.codingstrain.springcloud.sample.libraryapp.books.BookException;
import com.codingstrain.springcloud.sample.libraryapp.books.dto.BookInfo;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/bookInfo", params = { "authorName", "bookTitle" })
    public BookInfo findBookInfoByTitle(@RequestParam("authorName") String authorName, @RequestParam("bookTitle") String bookTitle) {
        try {
            return bookService.findBookInfoByRestTemplate("http://localhost:8080/library/author", "http://localhost:8080/library/review", authorName,
                bookTitle);
        } catch (BookException e) {
            logger.error("", e);
        }
        return null;
    }

    @GetMapping(value = "/book/{title}")
    public Optional<Book> findByTitle(@PathVariable("title") String title) {
        return bookService.findByTitle(title);
    }

}