package com.bilgeadam.onlinefoodapp.jwt.resource;

class AuthenticationException extends RuntimeException {
    AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}