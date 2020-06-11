package com.bilgeadam.mobilefoodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.data.Delivery;
import com.bilgeadam.mobilefoodapp.data.Meal;

import java.util.List;

public class StatusAdapter extends ArrayAdapter<Delivery> {

    private List<Delivery> deliveryList;

    public StatusAdapter(@NonNull Context context, List<Delivery> deliveryList) {
        super(context, R.layout.delivery_order);
        this.deliveryList = deliveryList;
    }

    @Override
    public int getCount() {
        return this.deliveryList.size();
    }

    @Override
    public Delivery getItem(int index) {
        return this.deliveryList.get(index);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the view
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.delivery_order, parent, false);

        Delivery delivery = deliveryList.get(position);

        TextView mealName = itemView.findViewById(R.id.date_txt);
        mealName.setText(delivery.getOrderDate());

        TextView mealDesc = itemView.findViewById(R.id.tip_txt);
        mealDesc.setText(delivery.getTip());

        return itemView;
    }

}
