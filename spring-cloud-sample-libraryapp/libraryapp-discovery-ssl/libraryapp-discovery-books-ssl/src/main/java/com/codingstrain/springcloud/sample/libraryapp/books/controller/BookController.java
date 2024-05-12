package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @GetMapping(value = "/ping")
    public String ping() {
        return "Ping executed!";
    }


}