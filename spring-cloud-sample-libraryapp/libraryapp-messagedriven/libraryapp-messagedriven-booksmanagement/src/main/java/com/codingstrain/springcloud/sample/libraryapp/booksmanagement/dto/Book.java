package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto;


public class Book {

    private Long id;

    private String title;

    private String genre;

    public String getGenre() {
        return genre;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
