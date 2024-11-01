package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto.Author;
import com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto.Book;
import com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto.BookInfo;

@RestController
@RequestMapping("/booksmanagement")
public class BooksManagementController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping(value = "/sendBookInfo")
    public void newBookItem() {
        BookInfo bookInfo = new BookInfo();
        Book book = new Book();
        book.setTitle("The Naked Sun");
        book.setGenre("Sci-fi");
        Author author = new Author();
        author.setName("Isaac Asimov");
        author.setBiographyInfo("Biography Info");

        System.out.println("Sending " + bookInfo);
        streamBridge.send("sendBookInfo-out-0", bookInfo);
    }



}