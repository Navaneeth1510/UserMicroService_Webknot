package com.majorAssignment.User.Exceptions;

public class MetroCardNotFoundException extends RuntimeException {
    public MetroCardNotFoundException(Long userId) {
        super("User with ID " + userId + " does not have an active metro card.");
    }
}

