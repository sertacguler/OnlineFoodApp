package com.bilgeadam.mobilefoodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<Meal> campaignMealList = new ArrayList<>();

    public ImagePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return !campaignMealList.isEmpty() ? campaignMealList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    public List<Meal> getCampaignMealList() {
        return campaignMealList;
    }

    public void setCampaignMealList(List<Meal> campaignMealList) {
        this.campaignMealList = campaignMealList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_image, null);
        ImageView imageView = view.findViewById(R.id.image);
        String url = !campaignMealList.isEmpty() ? campaignMealList.get(position).getPhoto() : null;
        Glide.with(context)
                .load(url)
                .placeholder(context.getResources().getDrawable(R.color.cardview_dark_background))
                .into(imageView);

        //View cardView = LayoutInflater.from(context).inflate(R.layout.layout_image_slider, null);
        String waterMarkStr = !campaignMealList.isEmpty() ? campaignMealList.get(position).getPrice().toString() : null;
        TextView waterMarkText = view.findViewById(R.id.water_mark);
        waterMarkText.setText(waterMarkStr);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }
}