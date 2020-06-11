package com.bilgeadam.mobilefoodapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

    @JsonProperty("token")
    private String token;

    public JwtTokenResponse() {
    }

    public JwtTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}