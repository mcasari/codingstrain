package com.codingstrain.minimalrest.h2.actuator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codingstrain.minimalrest.h2.actuator.model.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
	List<Book> findByAuthor(String author);
}
