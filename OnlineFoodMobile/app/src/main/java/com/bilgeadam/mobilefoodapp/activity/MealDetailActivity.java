package com.bilgeadam.mobilefoodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bilgeadam.mobilefoodapp.MainActivity;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bilgeadam.mobilefoodapp.service.CartService;
import com.bilgeadam.mobilefoodapp.utililty.RetrofitClient;
import com.bumptech.glide.Glide;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailActivity extends AppCompatActivity {

    private Boolean isLoggedIn = false;
    private Button btnAdd;
    private Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mealImage = findViewById(R.id.meal_image);
        TextView mealName = findViewById(R.id.meal_name);
        TextView mealDetail = findViewById(R.id.meal_detail);
        TextView mealPrice = findViewById(R.id.meal_price);
        btnAdd = findViewById(R.id.btnAdd);

        isLoggedIn = MainActivity.getIsLoggedIn();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn == true) { //Getter Setter methodları ile eriştim LoginActivityden mainactivitye gidiyor mainde getset yaptı
                    getAddtoCart(meal.getCode());
                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                    startActivity(intent);
                } else {
                    //Login sayfasına yönlendir.
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Log.e("false", isLoggedIn.toString());
                }
            }
        });

        meal = (Meal) getIntent().getSerializableExtra("meal");
        if (meal != null) {
            Glide.with(this)
                    .load(meal.getPhoto())
                    .placeholder(getResources().getDrawable(R.color.cardview_dark_background, null))
                    .into(mealImage);
            mealName.setText(meal.getName());
            mealDetail.setText(meal.getDetail());
            mealPrice.setText(String.valueOf(meal.getPrice() + " TL"));
        }
    }

    private void getAddtoCart(String mealCode) {
        CartService cartService = RetrofitClient.getRetrofitInstance(this).create(CartService.class);
        cartService.addtoCart(mealCode).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
