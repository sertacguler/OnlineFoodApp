package com.bilgeadam.mobilefoodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.data.Delivery;
import com.bilgeadam.mobilefoodapp.dto.Meal;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class StatusListRecyclerAdapter extends RecyclerView.Adapter<StatusListRecyclerAdapter.DeliveryViewHolder> {
    private List<Delivery> deliveryList;
    private Context context;

    public StatusListRecyclerAdapter(Context context, ArrayList<Delivery> deliveries) {
        this.context = context;
        this.deliveryList = deliveries;
    }

    @NonNull
    @Override
    public StatusListRecyclerAdapter.DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_order, parent, false);
        return new StatusListRecyclerAdapter.DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusListRecyclerAdapter.DeliveryViewHolder holder, int position) {
        holder.setData(deliveryList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    class DeliveryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date_txt, dalivered_txt, tip_txt, t_tip_txt;

        DeliveryViewHolder(View itemView) {
            super(itemView);
            date_txt = itemView.findViewById(R.id.date_txt);
            dalivered_txt = itemView.findViewById(R.id.deliverDate_txt);
            t_tip_txt = itemView.findViewById(R.id.t_tip_txt);
            tip_txt = itemView.findViewById(R.id.tip_txt);
        }

        void setData(Delivery delivery, int position) {
            this.date_txt.setText(delivery.getOrderDate());

            if (delivery.getDeliveredDate() != null) {
                this.dalivered_txt.setText(delivery.getDeliveredDate());
                t_tip_txt.setVisibility(View.VISIBLE);
                tip_txt.setVisibility(View.VISIBLE);
                if (delivery.getTip() != null) {
                    this.tip_txt.setText(delivery.getTip());
                } else {
                    tip_txt.setTextSize(18);
                    this.tip_txt.setText(" - ");
                }
            } else {
                this.dalivered_txt.setText("Hazırlanıyor....");
                t_tip_txt.setVisibility(View.INVISIBLE);
                tip_txt.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
        }
    }
}
