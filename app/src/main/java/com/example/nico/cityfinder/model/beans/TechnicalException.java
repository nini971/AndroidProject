package com.example.nico.cityfinder.model.beans;

/**
 * Created by Nicolas Th on 20/06/2017.
 */

public class TechnicalException extends Exception {

    private String userMessage;

    public TechnicalException(String userMessage) {
        super(userMessage);
        this.userMessage = userMessage;


    }

    public TechnicalException(Throwable cause, String userMessage) {
        super(cause);
        this.userMessage = userMessage;
    }

    public TechnicalException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public TechnicalException(String message, Throwable cause, String userMessage) {
        super(message, cause);
        this.userMessage = userMessage;
    }


    public String getUserMessage() {
        return userMessage;
    }
}
