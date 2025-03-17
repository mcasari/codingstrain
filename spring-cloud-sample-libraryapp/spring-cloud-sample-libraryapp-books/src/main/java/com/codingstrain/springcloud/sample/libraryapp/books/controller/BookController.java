package com.codingstrain.springcloud.sample.libraryapp.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.service.BookService;


@RestController
@RequestMapping("/library")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/book")
	public List<Book> findAll() {
		return bookService.findAll();
	}

	@GetMapping("/book/{id}")
	public Book findById(@PathVariable("id") String id) {
		return bookService.findById(id);
	}

	@PostMapping("/book")
	public Book add(@RequestBody Book book) {
		return bookService.save(book);
	}

	@PutMapping("/book")
	public Book update(@RequestBody Book book) {
		return bookService.save(book);
	}
	
	@DeleteMapping("/book/{id}")
	public void deleteByTitle(@PathVariable("id") String id) {
		bookService.deleteById(id);
	}
	
	@GetMapping(value = "/book", params = {"author"})
	public List<Book> findByAuthor(@RequestParam("author") String author) {
		return bookService.findByAuthor(author);
	}
	
	
}