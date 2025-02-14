package com.majorAssignment.User.Exceptions;


public class TravelHistoryException extends RuntimeException {
    public TravelHistoryException(String message) {
        super(message);
    }

    public TravelHistoryException(String message, Throwable cause) {
        super(message, cause);
    }
}

