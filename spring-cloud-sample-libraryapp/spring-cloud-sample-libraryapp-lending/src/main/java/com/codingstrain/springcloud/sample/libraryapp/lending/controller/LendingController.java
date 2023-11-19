package com.codingstrain.springcloud.sample.libraryapp.lending.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.lending.model.Lending;
import com.codingstrain.springcloud.sample.libraryapp.lending.service.LendingService;

@RestController
@RequestMapping("/library")
public class LendingController {

	@Autowired
	private LendingService bookService;

	@GetMapping("/book")
	public List<Lending> findAll() {
		return null;
	}

	@GetMapping("/book/{id}")
	public Lending findById(@PathVariable("id") String id) {
		return bookService.findById(id);
	}


	
	
}