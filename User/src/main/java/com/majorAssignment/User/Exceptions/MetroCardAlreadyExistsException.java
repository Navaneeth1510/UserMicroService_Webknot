package com.majorAssignment.User.Exceptions;

public class MetroCardAlreadyExistsException extends RuntimeException {
    public MetroCardAlreadyExistsException(Long userId) {
        super("User with ID " + userId + " already has a metro card.");
    }
}

