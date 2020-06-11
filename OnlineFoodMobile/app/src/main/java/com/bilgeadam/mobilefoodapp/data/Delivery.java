package com.bilgeadam.mobilefoodapp.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Delivery implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("deliveryStatus")
    private Boolean deliveryStatus;

    @JsonProperty("tip")
    private String tip;

    @JsonProperty("deliveredDate")
    private String deliveredDate;

    @JsonProperty("orderDate")
    private String orderDate;

    public Delivery() {
    }

    public Delivery(Boolean deliveryStatus, String tip, String deliveredDate, String orderDate) {
        this.deliveryStatus = deliveryStatus;
        this.tip = tip;
        this.deliveredDate = deliveredDate;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

}
