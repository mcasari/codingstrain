package com.codingstrain.springcloud.sample.libraryapp.books.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Book")
@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String title;

    @Column
    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}