package com.codingstrain.springcloud.sample.libraryapp.books.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {

    public Optional<Book> findByTitle(String title);
}
