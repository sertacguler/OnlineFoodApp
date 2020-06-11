package com.bilgeadam.mobilefoodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.adapter.StatusListRecyclerAdapter;
import com.bilgeadam.mobilefoodapp.custom.ClickableViewPager;
import com.bilgeadam.mobilefoodapp.data.Delivery;
import com.bilgeadam.mobilefoodapp.service.DeliveryDataService;
import com.bilgeadam.mobilefoodapp.utililty.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveredActivity extends AppCompatActivity {

    private StatusListRecyclerAdapter statusAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ArrayList<Delivery> deliveries = new ArrayList<>();

        deliveries.add(new Delivery(false, "5", "asd", "2019-11-06 13-46-10"));
        deliveries.add(new Delivery(false, "2", "asd", "2019-11-06 13-46-10"));
        deliveries.add(new Delivery(false, "7", "asd", "2019-11-06 13-46-10"));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        statusAdapter = new StatusListRecyclerAdapter(this, deliveries);
        mRecyclerView.setAdapter(statusAdapter);

        getDelivery();

    }

    private void getDelivery() {
        DeliveryDataService deliveryDataService = RetrofitClient.getRetrofitInstance(this).create(DeliveryDataService.class);
        deliveryDataService.getDelivered().enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {
                statusAdapter.setDeliveryList(response.body());
                statusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
