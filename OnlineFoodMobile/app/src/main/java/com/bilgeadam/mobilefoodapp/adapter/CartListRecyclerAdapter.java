package com.bilgeadam.mobilefoodapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.activity.MealDetailActivity;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bilgeadam.mobilefoodapp.service.CartService;
import com.bilgeadam.mobilefoodapp.utililty.RetrofitClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListRecyclerAdapter extends RecyclerView.Adapter<CartListRecyclerAdapter.MealViewHolder> {

    private List<Meal> mMealList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    double totalPrice = 0;

    public CartListRecyclerAdapter(Context context, ArrayList<Meal> meals, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mMealList = meals;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_order, parent, false);
        return new MealViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        holder.setData(mMealList.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // Meal Detail
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MealDetailActivity.class);
                intent.putExtra("meal", mMealList.get(position));
                context.startActivity(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // Order Delete
            @Override
            public void onClick(View v) {
                Meal meal = mMealList.get(position);
                mMealList.remove(meal);
                getDeleteItemFromCart(meal.getCode());
                notifyItemRemoved(position);
            }
        });

//        holder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pay();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mMealList.size();
    }

    public void setMealList(List<Meal> mMealList) {
        this.mMealList = mMealList;
    }

    class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mealName, mealPrice;
        ImageView mealImage;
        OnItemClickListener onItemClickListener;
        ImageView imgDelete;
        Button btn;

        MealViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.meal_image);
            mealName = itemView.findViewById(R.id.meal_name);
            mealPrice = itemView.findViewById(R.id.meal_price);
            imgDelete = itemView.findViewById(R.id.meal_delete);
            btn = itemView.findViewById(R.id.button);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        void setData(Meal meal, int position) {
            this.mealName.setText(meal.getName());
            this.mealPrice.setText(meal.getPrice().toString() + " TL");
            Glide.with(context)
                    .load(meal.getPhoto())
                    .centerCrop()
                    .into(mealImage);
        }

        @Override
        public void onClick(View v) {
            Log.e("View: ", v.toString());
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void getDeleteItemFromCart(String mealCode) {
        CartService cartService = RetrofitClient.getRetrofitInstance(context).create(CartService.class);
        cartService.deleteItemFromCart(mealCode).enqueue(new Callback<ResponseBody>() {
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