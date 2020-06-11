package com.bilgeadam.mobilefoodapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Meal implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Long price;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("detail")
    private String detail;

    public Meal() {
    }

    public Meal(String code, String name, Long price, String photo, String detail) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", photo='" + photo + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}

