package com.codingstrain.springcloud.sample.libraryapp.authors.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Author")
@NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private Integer id;

	@Column
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}