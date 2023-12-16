package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.books.client.AuthorClient;
import com.codingstrain.springcloud.sample.libraryapp.books.client.ReviewClient;
import com.codingstrain.springcloud.sample.libraryapp.books.dto.BookInfo;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Author;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Review;
import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorClient authorClient;

    @Autowired
    private ReviewClient reviewClient;

    @GetMapping(value = "/bookInfo", params = { "authorName", "bookTitle" })
    public BookInfo findBookInfoByTitle(@RequestParam("authorName") String authorName, @RequestParam("bookTitle") String bookTitle) {
        Optional<Author> author = authorClient.findByName(authorName);
        List<Review> reviews = reviewClient.findByBookTitle(bookTitle);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setAuthorBiography(author.get()
            .getBiography());
        bookInfo.setAuthorName(authorName);
        List<String> reviewContents = reviews.stream()
            .map(item -> item.getContent())
            .collect(Collectors.toList());
        bookInfo.setTitle(bookTitle);
        bookInfo.setBookReviews(reviewContents);
        return bookInfo;
    }

    @GetMapping(value = "/book/{title}")
    public Optional<Book> findByTitle(@PathVariable("title") String title) {
        return bookService.findByTitle(title);
    }

}