package com.codingstrain.springcloud.sample.libraryapp.authors.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.authors.service.AuthorService;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PreAuthorize("hasAuthority('SCOPE_TESTSCOPE')")
    @GetMapping("/author/{name}")
    public Optional<Author> findByName(@PathVariable("name") String name) {
        return authorService.findByName(name);
    }

}