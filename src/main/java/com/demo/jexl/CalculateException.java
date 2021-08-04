package com.demo.jexl;

public class CalculateException extends Exception {
    public CalculateException() {
        super();
    }

    public CalculateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CalculateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculateException(String message) {
        super(message);
    }

    public CalculateException(Throwable cause) {
        super(cause);
    }
}
