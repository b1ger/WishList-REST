package com.wishlist.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Email already in use");
    }
}
