package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto;


public class Author {

    private Long id;

    private String name;

    private String biographyInfo;

    public String getBiographyInfo() {
        return biographyInfo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setBiographyInfo(String biographyInfo) {
        this.biographyInfo = biographyInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
