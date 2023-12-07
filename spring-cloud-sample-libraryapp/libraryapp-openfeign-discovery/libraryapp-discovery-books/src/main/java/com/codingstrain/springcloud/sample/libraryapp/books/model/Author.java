package com.codingstrain.springcloud.sample.libraryapp.books.model;

import java.io.Serializable;

public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String biography;

    public String getBiography() {
        return biography;
    }

    public String getName() {
        return name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setName(String name) {
        this.name = name;
    }

}