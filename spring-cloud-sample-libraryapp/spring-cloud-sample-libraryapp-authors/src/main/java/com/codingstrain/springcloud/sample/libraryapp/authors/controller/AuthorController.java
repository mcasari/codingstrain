package com.codingstrain.springcloud.sample.libraryapp.authors.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.authors.model.Author;
import com.codingstrain.springcloud.sample.libraryapp.authors.service.AuthorService;

@RestController
@RequestMapping("/library")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping("/author")
	public List<Author> findAll() {
		return authorService.findAll();
	}

	@GetMapping("/author/{firstName}/{lastName}")
	public List<Author> findByName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
		return authorService.findByName(firstName, lastName);
	}

}