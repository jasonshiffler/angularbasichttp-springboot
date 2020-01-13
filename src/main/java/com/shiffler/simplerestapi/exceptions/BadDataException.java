package com.shiffler.simplerestapi.exceptions;

public class BadDataException extends Exception {

    public BadDataException() {
        super("Bad data");
    }

    public BadDataException(String message) {
        super(message);
    }
}
