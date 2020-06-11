package com.bilgeadam.mobilefoodapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bilgeadam.mobilefoodapp.MainActivity;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.dto.JwtTokenRequest;
import com.bilgeadam.mobilefoodapp.dto.JwtTokenResponse;
import com.bilgeadam.mobilefoodapp.service.AuthenticateService;
import com.bilgeadam.mobilefoodapp.utililty.RetrofitClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Console;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username_txt, password_txt;
    private CheckBox chRememberMe;
    private String username, password;
    private Boolean isLoggedIn = false;
    private String rememberMe = "false";
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_txt = (EditText) findViewById(R.id.username_txt);
        password_txt = (EditText) findViewById(R.id.password_txt);
        chRememberMe = (CheckBox) findViewById(R.id.chRememberMe);

        intent = new Intent(this, MainActivity.class);

        chRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()) {
                    rememberMe = "true";
                } else {
                    rememberMe = "false";
                }
            }
        });

    }

    public void showlogin(View view) {
        username = username_txt.getText().toString();
        password = password_txt.getText().toString();
        Log.e("asd", username + password);

        if (username != "" && password != "") { //Burada textbox lar boş bırakılamasın diye koydum
            authenticate(username, password, rememberMe); // ama hatalı girsende boş bıraksada hata veriyor
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // alert dialog başlığını tanımlıyoruz.
            alertDialogBuilder.setTitle("Login Page");
            // alert dialog özelliklerini oluşturuyoruz.
            alertDialogBuilder
                    .setMessage("Kullanıcı adı ve Şifre boş bırakılamaz")
                    .setCancelable(false)
                    .setIcon(R.mipmap.ic_launcher_round)
                    // Evet butonuna tıklanınca yapılacak işlemleri buraya yazıyoruz.
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            // alert dialog nesnesini oluşturuyoruz
            AlertDialog alertDialog = alertDialogBuilder.create();
            // alerti gösteriyoruz
            alertDialog.show();
        }
    }

    private void authenticate(String username, String password, String rememberMe) {
        AuthenticateService authenticateService = RetrofitClient.getRetrofitInstance(this).create(AuthenticateService.class);
        authenticateService.authenticate(new JwtTokenRequest(username, password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = response.body().string();
                    JwtTokenResponse jwtTokenResponse = new ObjectMapper().readValue(responseString, JwtTokenResponse.class);
                    SharedPreferences sharedPref = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                    sharedPref.edit().putString("TOKEN", jwtTokenResponse.getToken()).apply();
                    sharedPref.edit().putString("USERNAME", username).apply();
                    sharedPref.edit().putString("REMEMBERME", rememberMe).apply();

                    isLoggedIn = true;
                    if (isLoggedIn)
                        intent.putExtra("isLoggedIn", isLoggedIn);
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}