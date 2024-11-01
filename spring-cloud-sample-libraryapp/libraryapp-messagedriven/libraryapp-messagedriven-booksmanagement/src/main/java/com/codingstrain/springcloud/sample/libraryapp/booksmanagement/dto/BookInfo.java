package com.codingstrain.springcloud.sample.libraryapp.booksmanagement.dto;


public class BookInfo {

    private Book book;

    private Author author;

    public Author getAuthor() {
        return author;
    }

    public Book getBook() {
        return book;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
