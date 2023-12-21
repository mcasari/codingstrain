package com.codingstrain.springcloud.sample.libraryapp.authors.rest;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codingstrain.springcloud.sample.libraryapp.authors.model.Author;


public interface AuthorRESTService {
	
    @GetMapping("/author/{name}")
    public Optional<Author> findByName(@PathVariable("name") String name);

}