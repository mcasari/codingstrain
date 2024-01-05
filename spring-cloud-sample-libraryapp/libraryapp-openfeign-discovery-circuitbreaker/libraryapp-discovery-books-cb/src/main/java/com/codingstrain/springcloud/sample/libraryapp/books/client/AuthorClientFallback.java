package com.codingstrain.springcloud.sample.libraryapp.books.client;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

@Component
class AuthorClientFallback implements AuthorClient {

    @Override
    public Optional<Author> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public String getInstance() {
        return "";
    }

}