package com.codingstrain.springcloud.sample.libraryapp.borrowers.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.borrowers.model.Borrower;
import com.codingstrain.springcloud.sample.libraryapp.borrowers.repository.BorrowerRepository;

@Service("bookService")
public class BorrowerService {

	Logger logger = LoggerFactory.getLogger(BorrowerService.class);
	
	@Autowired
	private BorrowerRepository bookRepository;

	public List<Borrower> findAll() {
		return bookRepository.findAll();
	}
	
	public Borrower findById(String id) {
		return bookRepository.findById(id).get();
	}
	
	public Borrower save(Borrower book) {
		return bookRepository.save(book);
	}
	
	public Borrower update(Borrower book) {
		return bookRepository.save(book);
	}
	
	public void deleteById(String id) {
		bookRepository.deleteById(id);
	}
	
	public List<Borrower> findByAuthor(String author) {
		return bookRepository.findByAuthor(author);
	}
	
}
