package com.bilgeadam.mobilefoodapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.adapter.CartListRecyclerAdapter;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bilgeadam.mobilefoodapp.service.CartService;
import com.bilgeadam.mobilefoodapp.utililty.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CartActivity extends AppCompatActivity implements CartListRecyclerAdapter.OnItemClickListener {

    private CartListRecyclerAdapter cartListRecyclerAdapter;
    ArrayList<Meal> mealList = new ArrayList<>();

    private TextView totalPriceTxt;
    double totalPrice = 0;
    private Button btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        totalPriceTxt = findViewById(R.id.totalPrice);
        btn = findViewById(R.id.button);

        //recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cartListRecyclerAdapter = new CartListRecyclerAdapter(this, mealList, this);
        mRecyclerView.setAdapter(cartListRecyclerAdapter);

        getCart();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
        intent.putExtra("meal", mealList.get(position));
        startActivity(intent);
    }

    private void getCart() {
        CartService cartService = RetrofitClient.getRetrofitInstance(this).create(CartService.class);
        cartService.getCart().enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                cartListRecyclerAdapter.setMealList(response.body());

                mealList.addAll(response.body());
                for (Meal meal : mealList) {
                    System.err.println(meal.toString());
                    totalPrice = totalPrice + Double.valueOf(meal.getPrice());
                }
                totalPriceTxt.setText(String.valueOf(totalPrice) + " TL");

                cartListRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void pay() {
        CartService cartService = RetrofitClient.getRetrofitInstance(this).create(CartService.class);
        cartService.pay().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                alertDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Online Food");
        builder.setMessage("Afiyet olsun :)");
        builder.setPositiveButton("Teşekkürler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}