package com.bilgeadam.mobilefoodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.activity.MealDetailActivity;
import com.bilgeadam.mobilefoodapp.activity.MealMenuActivity;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MealListRecyclerAdapter extends RecyclerView.Adapter<MealListRecyclerAdapter.MealViewHolder> {

    private List<Meal> mMealList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public MealListRecyclerAdapter(Context context, ArrayList<Meal> meals, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mMealList = meals;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_meal, parent, false);
        return new MealViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        holder.setData(mMealList.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MealDetailActivity.class);
                intent.putExtra("meal", mMealList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMealList.size();
    }

    public void setMealList(List<Meal> mMealList) {
        this.mMealList = mMealList;
    }

    class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mealName, mealDescription;
        ImageView mealImage;
        OnItemClickListener onItemClickListener;

        MealViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.meal_image);
            mealName = itemView.findViewById(R.id.meal_name);
            mealDescription = itemView.findViewById(R.id.meal_description);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

        }

        void setData(Meal meal, int position) {
            this.mealName.setText(meal.getName());
            this.mealDescription.setText(meal.getDetail());
            Glide.with(context)
                    .load(meal.getPhoto())
                    .centerCrop()
                    .into(mealImage);
        }

        @Override
        public void onClick(View v) {

            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}