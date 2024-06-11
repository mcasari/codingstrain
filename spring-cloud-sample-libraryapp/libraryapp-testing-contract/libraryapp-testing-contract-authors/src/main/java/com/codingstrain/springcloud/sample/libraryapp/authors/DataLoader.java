package com.codingstrain.springcloud.sample.libraryapp.authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.codingstrain.springcloud.sample.libraryapp.authors.repository.AuthorRepository;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

@Component
public class DataLoader implements ApplicationRunner {

    private AuthorRepository authorRepository;

    @Autowired
    public DataLoader(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Author author = new Author();
        author.setName("Goethe");
        author.setBiography("Bio info");
        authorRepository.save(author);
    }
}