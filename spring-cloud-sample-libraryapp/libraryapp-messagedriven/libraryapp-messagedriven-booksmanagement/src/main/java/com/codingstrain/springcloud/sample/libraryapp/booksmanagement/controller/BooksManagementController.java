package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booksmanagement")
public class BooksManagementController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping(value = "/newBookItem")
    public void newBookItem() {
        BookInfo bookInfo = new BookInfo();
        System.out.println("Sending " + bookInfo);
        streamBridge.send("toStream", bookInfo);
    }

    class BookInfo {
        String bookTitle;
        String author;

        public String getAuthor() {
            return author;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        @Override
        public String toString() {
            return "BookInfo [bookTitle=" + bookTitle + ", author=" + author + "]";
        }

    }

}