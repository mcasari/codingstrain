package com.codingstrain.springcloud.sample.libraryapp.borrowers.model;

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
@Table(name="Borrower")
@NamedQuery(name = "Borrower.findAll", query = "SELECT b FROM Borrower b")
public class Borrower implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private String userID;

	@Column
	private String firstName;

	@Column
	private String lastName;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}