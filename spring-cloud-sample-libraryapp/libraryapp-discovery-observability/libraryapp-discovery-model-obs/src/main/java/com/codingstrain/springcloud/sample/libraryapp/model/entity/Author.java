package com.codingstrain.springcloud.sample.libraryapp.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Author")
@NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String name;

    @Column
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