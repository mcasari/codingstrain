package com.codingstrain.springcloud.sample.libraryapp.authors.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.authors.repository.AuthorRepository;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

import io.micrometer.observation.annotation.Observed;

@Service("authorService")
public class AuthorService {

    Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Observed(name = "authorInfo", contextualName = "getting-author-info", lowCardinalityKeyValues = { "authorType", "Poet" })
    public Optional<Author> findByName(String name) {
        return authorRepository.findById(name);
    }

}
