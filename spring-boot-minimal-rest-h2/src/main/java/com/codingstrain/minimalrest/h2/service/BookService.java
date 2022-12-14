package com.codingstrain.minimalrest.h2.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.minimalrest.h2.model.Book;
import com.codingstrain.minimalrest.h2.repository.BookRepository;


@Service("bookService")
public class BookService {

	Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRepository bookRepository;

	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	public Book findById(String id) {
		return bookRepository.findById(id).get();
	}
	
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	public Book update(Book book) {
		return bookRepository.save(book);
	}
	
	public void deleteById(String id) {
		bookRepository.deleteById(id);
	}
	
	public List<Book> findByAuthor(String author) {
		return bookRepository.findByAuthor(author);
	}
	
}
