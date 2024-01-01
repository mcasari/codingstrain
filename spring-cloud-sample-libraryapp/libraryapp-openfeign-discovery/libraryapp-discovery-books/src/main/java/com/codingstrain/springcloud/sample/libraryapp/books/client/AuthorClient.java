package com.codingstrain.springcloud.sample.libraryapp.books.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codingstrain.springcloud.sample.libraryapp.authors.model.Author;

@FeignClient(name = "author-service")
public interface AuthorClient {

    @GetMapping("/author/{name}")
    public Optional<Author> findByName(@PathVariable("name") String name);

}