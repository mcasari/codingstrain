package com.codingstrain.springcloud.sample.libraryapp.authors.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.authors.model.Author;
import com.codingstrain.springcloud.sample.libraryapp.authors.repository.AuthorRepository;

@Service("authorService")
public class AuthorService {

    Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findByName(String name) {
        return authorRepository.findById(name);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

}
