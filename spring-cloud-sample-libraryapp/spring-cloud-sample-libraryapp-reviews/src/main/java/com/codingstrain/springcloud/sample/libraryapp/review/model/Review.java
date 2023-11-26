package com.codingstrain.springcloud.sample.libraryapp.review.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;



@Entity
@Table(name="Review")
@NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private String isbn;
	
	@Column
	private String content;	

}