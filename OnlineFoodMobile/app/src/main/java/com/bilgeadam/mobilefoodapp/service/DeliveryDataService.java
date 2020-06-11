package com.bilgeadam.mobilefoodapp.service;

import com.bilgeadam.mobilefoodapp.data.Delivery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DeliveryDataService {

    @GET("/meal/findYolda")
    Call<List<Delivery>> getDeliverys();

    @GET("/meal/findGeldi")
    Call<List<Delivery>> getDelivered();

}
