package com.kozich.xmltask.exception;

public class PaperException extends Exception {
    public PaperException() {
    }

    public PaperException(String message) {
        super(message);
    }

    public PaperException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaperException(Throwable cause) {
        super(cause);
    }
}
