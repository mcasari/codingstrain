package com.codingstrain.springcloud.sample.libraryapp.lending.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;



@Entity
@Table(name="Lending")
@NamedQuery(name = "Lending.findAll", query = "SELECT l FROM Lending l")
public class Lending implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LendingPK lendingPK;
	
	@Column
	private Date startDate;

	@Column
	private Date endDate;
}