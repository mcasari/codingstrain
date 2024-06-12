package com.codingstrain.springcloud.sample.libraryapp.books;

public class BookException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookException(Exception e) {
        this.initCause(e);
    }

    public BookException(String string, Exception e) {
        this.initCause(e);
    }

}
