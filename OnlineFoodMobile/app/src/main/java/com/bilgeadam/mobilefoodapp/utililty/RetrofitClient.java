package com.bilgeadam.mobilefoodapp.utililty;

import android.content.Context;
import android.content.SharedPreferences;

import com.bilgeadam.mobilefoodapp.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8034";

    public static Retrofit getRetrofitInstance(final Context context) {
        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().
                connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        SharedPreferences sharedPref = context.getSharedPreferences(
                                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        String token = sharedPref.getString("TOKEN", null);
                        if (token != null) {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                        return chain.proceed(chain.request());
                    }
                }).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(okHttpClient)
                .build();
        return retrofit;
    }
}