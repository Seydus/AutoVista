package com.example.autovista.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;

public class CarViewHolder extends RecyclerView.ViewHolder {
    public TextView text_CarItem;
    public CardView card_View;

    public CarViewHolder(@NonNull View ItemView){
        super(ItemView);
        text_CarItem = ItemView.findViewById(R.id.car_item);
        card_View = ItemView.findViewById(R.id.main_container);
    }
}
