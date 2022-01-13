package com.example.springbatchdemo.exception;

public class CustomRetryException extends RuntimeException{
    public CustomRetryException() {
        super();
    }

    public CustomRetryException(String message) {
        super(message);
    }

    public CustomRetryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRetryException(Throwable cause) {
        super(cause);
    }

    protected CustomRetryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
