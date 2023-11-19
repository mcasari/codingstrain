package com.codingstrain.springcloud.sample.libraryapp.books.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


/**
 * The persistent class for the RMA_RETE database table.
 * 
 */
@Entity
@Table(name="Book")
@NamedQuery(name = "Rete.findAll", query = "SELECT r FROM Book r")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column
	private String title;

	@Column
	private String author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	

}