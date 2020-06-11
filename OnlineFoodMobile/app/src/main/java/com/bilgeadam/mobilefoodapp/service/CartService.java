package com.bilgeadam.mobilefoodapp.service;

import com.bilgeadam.mobilefoodapp.dto.Meal;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartService {

    @GET("/meal/findCartByCustomer_Id")
    Call<List<Meal>> getCart();

    @POST("/meal/add")
    Call<ResponseBody> addtoCart(@Body String id);

    @POST("/meal/DeleteByMealCodeId")
    Call<ResponseBody> deleteItemFromCart(@Body String id);

    @GET("/meal/odeme")
    Call<ResponseBody> pay();

}
