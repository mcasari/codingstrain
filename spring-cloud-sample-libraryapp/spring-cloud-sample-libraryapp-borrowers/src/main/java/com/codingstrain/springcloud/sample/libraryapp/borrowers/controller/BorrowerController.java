package com.codingstrain.springcloud.sample.libraryapp.borrowers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.borrowers.model.Borrower;
import com.codingstrain.springcloud.sample.libraryapp.borrowers.service.BorrowerService;

@RestController
@RequestMapping("/library")
public class BorrowerController {

	@Autowired
	private BorrowerService bookService;

	@GetMapping("/book")
	public List<Borrower> findAll() {
		return null;
	}

	@GetMapping("/book/{id}")
	public Borrower findById(@PathVariable("id") String id) {
		return bookService.findById(id);
	}


	
	
}