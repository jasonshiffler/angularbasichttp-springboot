package com.shiffler.simplerestapi.controlleradvice;

import lombok.Getter;

import java.util.Date;

@Getter //Exception Handler will not process correctly without have getters available
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


}
