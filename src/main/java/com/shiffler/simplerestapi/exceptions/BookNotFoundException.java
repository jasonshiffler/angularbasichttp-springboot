package com.shiffler.simplerestapi.exceptions;

public class BookNotFoundException extends Exception {

    public BookNotFoundException() {
        super("Book was not found");
    }

    public BookNotFoundException(String message) {
        super(message);
    }

}
