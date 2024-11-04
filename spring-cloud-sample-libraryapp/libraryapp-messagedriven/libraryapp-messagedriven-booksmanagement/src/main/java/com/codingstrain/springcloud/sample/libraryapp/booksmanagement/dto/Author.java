package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto;


public class Author {

    private String name;

    private String biographyInfo;

    public String getBiographyInfo() {
        return biographyInfo;
    }

    public String getName() {
        return name;
    }

    public void setBiographyInfo(String biographyInfo) {
        this.biographyInfo = biographyInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

}
