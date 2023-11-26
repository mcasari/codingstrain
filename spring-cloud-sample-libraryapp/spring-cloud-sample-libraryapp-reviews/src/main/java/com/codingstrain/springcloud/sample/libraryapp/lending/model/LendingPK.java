package com.codingstrain.springcloud.sample.libraryapp.lending.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LendingPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column
	private String userID;

	@Column
	private String isbn;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}