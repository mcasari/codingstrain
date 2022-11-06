package com.codingstrain.minimalrest.h2.model;

import java.io.Serializable;

import javax.persistence.*;


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
	
}