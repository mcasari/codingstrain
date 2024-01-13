package com.codingstrain.springcloud.sample.libraryapp.books.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.books.client.AuthorClient;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.repository.BookRepository;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

@Service("bookService")
public class BookService {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private AuthorClient authorClient;

    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Optional<Author> getAuthor(String name) {
        return authorClient.findByName(name);
    }

}
