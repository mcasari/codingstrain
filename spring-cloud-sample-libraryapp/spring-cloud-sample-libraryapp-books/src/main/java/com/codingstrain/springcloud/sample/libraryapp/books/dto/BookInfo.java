package com.codingstrain.springcloud.sample.libraryapp.books.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class BookInfo {
	
	@Id
	@Column
	private String isbn;
	
	@Column
	private String authorName;
	
	@Column
	private String authorBiography;
	
	@Column
	private List<String> bookReviews;

}