package com.wishlist.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String explanation) {
        super(explanation);
    }

    public UserNotActivatedException(String explanation, Throwable throwable) {
        super(explanation, throwable);
    }
}
