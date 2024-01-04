package com.codingstrain.springcloud.sample.libraryapp.books.dto;

import java.io.Serializable;
import java.util.List;


public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String authorName;

    private String authorBiography;

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