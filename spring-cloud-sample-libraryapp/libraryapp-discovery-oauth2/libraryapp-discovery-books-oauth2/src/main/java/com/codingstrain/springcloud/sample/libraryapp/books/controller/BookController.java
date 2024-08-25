package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);



    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping(value = "/authorInfo", params = { "authorName" })
    public String getAuthor(@RequestParam("authorName") String authorName) {
        return "authorInfo";
    }

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping(value = "/ping")
    public String ping() {
        return "ping";
    }

}