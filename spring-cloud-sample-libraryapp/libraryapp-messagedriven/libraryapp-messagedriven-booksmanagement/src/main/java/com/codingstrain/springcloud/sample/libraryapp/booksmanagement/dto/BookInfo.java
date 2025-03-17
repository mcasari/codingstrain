package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto;


public class BookInfo {

    private Long id;

    private Book book;

    private Author author;

    public Author getAuthor() {
        return author;
    }

    public Book getBook() {
        return book;
    }

    public Long getId() {
        return id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
