package com.codingstrain.springcloud.sample.libraryapp.books.dto;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Book")
@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String title;

    @Column
    private String authorName;

    @Column
    private String authorBiography;

    @Column
    private List<String> bookReviews;

    public String getAuthorBiography() {
        return authorBiography;
    }

    public String getAuthorName() {
        return authorName;
    }

    public List<String> getBookReviews() {
        return bookReviews;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthorBiography(String authorBiography) {
        this.authorBiography = authorBiography;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setBookReviews(List<String> bookReviews) {
        this.bookReviews = bookReviews;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}