package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);


    @GetMapping(value = "/bookOfTheMonth")
    public String getBookOfTheMonth() {
        return "The naked sun";
    }



}