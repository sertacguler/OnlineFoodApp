package com.bilgeadam.onlinefoodapp.jwt.resource;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

    private final String token;

    JwtTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}