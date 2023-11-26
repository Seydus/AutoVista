package com.example.autovista.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;

public class BestSellerViewHolder extends RecyclerView.ViewHolder {
    public TextView title_text;
    public TextView description;
    public ImageView pic;

    public BestSellerViewHolder(@NonNull View itemView) {
        super(itemView);

        title_text = itemView.findViewById(R.id.best_seller_title_text);
        description = itemView.findViewById(R.id.best_seller_description);
        pic = itemView.findViewById(R.id.best_seller_pic);
    }
}
