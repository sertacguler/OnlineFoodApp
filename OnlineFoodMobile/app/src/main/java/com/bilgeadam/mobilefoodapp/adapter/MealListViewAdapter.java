package com.bilgeadam.mobilefoodapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.data.Meal;

import java.util.List;

public class MealListViewAdapter extends ArrayAdapter<Meal> {

    private List<Meal> meals;

    public MealListViewAdapter(Context context, List<Meal> meals) {
        super(context, R.layout.card_meal);
        this.meals = meals;
    }

    @Override
    public int getCount() {
        return this.meals.size();
    }

    @Override
    public Meal getItem(int index) {
        return this.meals.get(index);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the view
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.card_meal, parent, false);

        // Get current package name
        Meal meal = meals.get(position);

        /*// Get the relative layout
        RelativeLayout relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl);*/

        // Display the app package name
        ImageView mealImage = itemView.findViewById(R.id.meal_image);
        mealImage.setImageDrawable(getContext().getDrawable(R.mipmap.ic_launcher));

        // Get the app label
        TextView mealName = itemView.findViewById(R.id.meal_name);
        mealName.setText(meal.getMealName());

        TextView mealDesc = itemView.findViewById(R.id.meal_description);
        mealDesc.setText(meal.getDescription());

        return itemView;
    }
}