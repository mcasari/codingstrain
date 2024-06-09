package com.codingstrain.springcloud.sample.libraryapp.books.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

@FeignClient(name = "author-service", url = "http://localhost:8091/authors")
public interface AuthorClient {

    @GetMapping(value = "/findAuthor", params = { "name" })
    public Optional<Author> findByName(@RequestParam("name") String name);

}