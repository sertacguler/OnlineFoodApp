package com.bilgeadam.mobilefoodapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bilgeadam.mobilefoodapp.MainActivity;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.adapter.MealListRecyclerAdapter;
import com.bilgeadam.mobilefoodapp.custom.ClickableViewPager;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bilgeadam.mobilefoodapp.service.MealDataService;
import com.bilgeadam.mobilefoodapp.utililty.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealMenuActivity extends AppCompatActivity implements MealListRecyclerAdapter.OnItemClickListener {

    private MealListRecyclerAdapter mMealAdapter;
    ArrayList<Meal> mealList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_menu);

        //recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMealAdapter = new MealListRecyclerAdapter(this, mealList, this);
        mRecyclerView.setAdapter(mMealAdapter);

        getMeals();

    }

    private void getMeals() {
        MealDataService mealDataService = RetrofitClient.getRetrofitInstance(this).create(MealDataService.class);
        mealDataService.getMeals().enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                mMealAdapter.setMealList(response.body());
                mMealAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
        intent.putExtra("meal", mealList.get(position));
        startActivity(intent);
    }
}
