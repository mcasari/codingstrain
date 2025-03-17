package com.codingstrain.springcloud.sample.libraryapp.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.repository.BookRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private BookRepository bookRepository;

    @Autowired
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Book book = new Book();
        book.setTitle("La Divina Commedia");
        book.setAuthorName("Dante Alighieri");
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Faust");
        book.setAuthorName("Goethe");
        bookRepository.save(book);
    }
}