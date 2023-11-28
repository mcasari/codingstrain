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
@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private String isbn;
	
	@Column
	private String title;
	
	@Column
	private String edition;

	@Column
    private String authorName;

	public String getAuthorName() {
        return authorName;
    }

	public String getEdition() {
		return edition;
	}

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}