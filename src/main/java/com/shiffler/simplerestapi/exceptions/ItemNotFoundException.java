package com.shiffler.simplerestapi.exceptions;

public class ItemNotFoundException extends Exception {

    public ItemNotFoundException() {
        super("Book was not found");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

}
