package com.codingstrain.springcloud.sample.libraryapp.lending.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.lending.model.Lending;
import com.codingstrain.springcloud.sample.libraryapp.lending.repository.LendingRepository;

@Service("bookService")
public class LendingService {

	Logger logger = LoggerFactory.getLogger(LendingService.class);
	
	@Autowired
	private LendingRepository bookRepository;

	public List<Lending> findAll() {
		return bookRepository.findAll();
	}
	
	public Lending findById(String id) {
		return bookRepository.findById(id).get();
	}
	
	public Lending save(Lending book) {
		return bookRepository.save(book);
	}
	
	public Lending update(Lending book) {
		return bookRepository.save(book);
	}
	
	public void deleteById(String id) {
		bookRepository.deleteById(id);
	}
	
	public List<Lending> findByAuthor(String author) {
		return bookRepository.findByAuthor(author);
	}
	
}
