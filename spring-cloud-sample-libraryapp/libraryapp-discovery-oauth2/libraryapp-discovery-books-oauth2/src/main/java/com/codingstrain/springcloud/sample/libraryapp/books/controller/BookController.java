package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);



    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping(value = "/bookOfTheMonth")
    public String getBookOfTheMonth() {
        return "bookOfTheMonth";
    }



}