package com.codingstrain.springcloud.sample.libraryapp.authors.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.authors.service.AuthorService;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping(value = "/findAuthor", params = { "name" })
    public Optional<Author> findByName(@RequestParam("name") String name) {
        return authorService.findByName(name);
    }

}