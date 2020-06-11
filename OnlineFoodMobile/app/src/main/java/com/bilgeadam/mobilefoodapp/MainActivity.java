package com.bilgeadam.mobilefoodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bilgeadam.mobilefoodapp.activity.CartActivity;
import com.bilgeadam.mobilefoodapp.activity.DeliveredActivity;
import com.bilgeadam.mobilefoodapp.activity.LoginActivity;
import com.bilgeadam.mobilefoodapp.activity.MealDetailActivity;
import com.bilgeadam.mobilefoodapp.activity.MealMenuActivity;
import com.bilgeadam.mobilefoodapp.activity.StatusActivity;
import com.bilgeadam.mobilefoodapp.adapter.ImagePagerAdapter;
import com.bilgeadam.mobilefoodapp.custom.ClickableViewPager;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bilgeadam.mobilefoodapp.service.AuthenticateService;
import com.bilgeadam.mobilefoodapp.service.MealDataService;
import com.bilgeadam.mobilefoodapp.utililty.AppUtils;
import com.bilgeadam.mobilefoodapp.utililty.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImagePagerAdapter imagePagerAdapter;
    private CircleIndicator circleIndicator;
    private ClickableViewPager viewPager;
    private List<Meal> campaignMealList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;

    private Button login_btn;

    private static Boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.loginbtn);

        refresh();

        Bundle extras = getIntent().getExtras(); //LoginActivty sayfasından isLoggedIn bilgisi gedimi.
        if (extras != null)
            isLoggedIn = extras.getBoolean("isLoggedIn");

        if (isLoggedIn) { // isLoggedIn true ise Buttonnun text i değişiyor
            login_btn.setText("Logout");
        } else {
            login_btn.setText("Login");
        }

        getCampaigns(); // Kampanyalar alınıyor
        configureSlider();
        swipeRefresh = findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(() -> {
            getCampaigns();
            swipeRefresh.setRefreshing(false);
        });

    }

    public void show(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.mealbtn:
                intent = new Intent(this, MealMenuActivity.class);
                intent.putExtra("isloggedIn", isLoggedIn);
                startActivity(intent);
                break;
            case R.id.ordersbtn:
                intent = new Intent(this, StatusActivity.class);
                if (isLoggedIn) // giriş yapıldıysa gir
                    startActivity(intent);
                break;
            case R.id.previousordersbtn:
                intent = new Intent(this, DeliveredActivity.class);
                if (isLoggedIn) // giriş yapıldıysa gir
                    startActivity(intent);
                break;
            case R.id.cartbtn:
                intent = new Intent(this, CartActivity.class);
                if (isLoggedIn) // giriş yapıldıysa gir
                    startActivity(intent);
                break;
            case R.id.loginbtn:
                if (!isLoggedIn) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    isLoggedIn = false;
                    SharedPreferences sharedPref = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.remove("REMEMBERME").apply();
//                    editor.remove("USERNAME").apply();
//                    editor.remove("TOKEN").apply();
//                    editor.commit();
//
                    editor.clear();
                    editor.commit();

                    System.exit(1);
                }
                break;
        }

    }

    private void configureSlider() {
        viewPager = findViewById(R.id.image_pager);
        imagePagerAdapter = new ImagePagerAdapter(this);
        viewPager.setAdapter(imagePagerAdapter);
        viewPager.setOnItemClickListener(position -> {
            if (!campaignMealList.isEmpty()) {
                Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
                intent.putExtra("meal", campaignMealList.get(position));
                startActivity(intent);
            }
        });
        AppUtils.automaticSlide(viewPager, imagePagerAdapter);
        circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(viewPager);
    }

    private void getCampaigns() {
        MealDataService mealDataService = RetrofitClient.getRetrofitInstance(this).create(MealDataService.class);
        mealDataService.getCampaigns().enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(@NonNull Call<List<Meal>> call, @NonNull Response<List<Meal>> response) {
                imagePagerAdapter.setCampaignMealList(response.body());
                campaignMealList = imagePagerAdapter.getCampaignMealList();
                imagePagerAdapter.notifyDataSetChanged();
                circleIndicator = findViewById(R.id.circle);
                circleIndicator.setViewPager(viewPager);
            }

            @Override
            public void onFailure(@NonNull Call<List<Meal>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(Boolean isLoggedIn) {
        MainActivity.isLoggedIn = isLoggedIn;
    }

    public void refresh() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String rememberMe = sharedPref.getString("REMEMBERME", "false");
        String token = sharedPref.getString("TOKEN", "");
        String userN = sharedPref.getString("USERNAME", "");

        if (rememberMe.equals("true") && !token.isEmpty() && !userN.isEmpty()) {
            AuthenticateService authenticateService = RetrofitClient.getRetrofitInstance(this).create(AuthenticateService.class);
            authenticateService.refreshToken(token).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    isLoggedIn = true;
                    login_btn.setText("Logout");

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    SharedPreferences sharedPref = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    sharedPref.edit().remove("REMEMBERME").apply();
                    sharedPref.edit().remove("USERNAME").apply();
                    sharedPref.edit().remove("TOKEN").apply();
                }
            });
        }
    }
}
