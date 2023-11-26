package com.codingstrain.springcloud.sample.libraryapp.authors.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.authors.model.Author;
import com.codingstrain.springcloud.sample.libraryapp.authors.repository.AuthorRepository;

@Service("authorService")
public class AuthorService {

	Logger logger = LoggerFactory.getLogger(AuthorService.class);
	
	@Autowired
	private AuthorRepository authorRepository;

	public List<Author> findAll() {
		return authorRepository.findAll();
	}
	
	public Author findById(String id) {
		return authorRepository.findById(id).get();
	}
	
	public Author save(Author author) {
		return authorRepository.save(author);
	}
	
	public Author update(Author book) {
		return authorRepository.save(book);
	}
	
	public void deleteById(String id) {
		authorRepository.deleteById(id);
	}
	
	public List<Author> findByName(String firstName, String lastName) {
		return authorRepository.findAllByFirstNameAndLastName(firstName, lastName);
	}
	
}
