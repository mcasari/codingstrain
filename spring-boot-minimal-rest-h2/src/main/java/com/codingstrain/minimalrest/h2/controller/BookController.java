package com.codingstrain.minimalrest.h2.controller;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.minimalrest.h2.model.Book;
import com.codingstrain.minimalrest.h2.service.BookService;  


@RestController  
public class BookController   
{  

	@Autowired  
	private BookService bookService;  

	@GetMapping("/book")  
	private List<Book> getAllBook()   
	{  
		return bookService.getAllBook();  
	}  
 
}  