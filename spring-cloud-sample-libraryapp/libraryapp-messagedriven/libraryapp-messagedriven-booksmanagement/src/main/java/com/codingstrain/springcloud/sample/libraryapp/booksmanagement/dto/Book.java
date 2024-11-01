package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto;


public class Book {

    private String title;

    private String genre;

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
