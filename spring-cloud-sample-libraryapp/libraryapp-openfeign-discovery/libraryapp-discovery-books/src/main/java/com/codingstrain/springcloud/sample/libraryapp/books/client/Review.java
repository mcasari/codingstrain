package com.codingstrain.springcloud.sample.libraryapp.books.client;

import java.io.Serializable;

public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bookTitle;

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