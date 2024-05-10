package com.codingstrain.springcloud.sample.libraryapp.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Review")
@NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String bookTitle;

    @Column
    private String content;

    public String getBookTitle() {
        return bookTitle;
    }

    public String getContent() {
        return content;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setContent(String content) {
        this.content = content;
    }

}