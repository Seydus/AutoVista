package com.example.autovista.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;

public class LatestReleaseViewHolder extends RecyclerView.ViewHolder {
    public TextView title_text;
    public TextView description;
    public ImageView pic;

    public LatestReleaseViewHolder(@NonNull View itemView) {
        super(itemView);

        title_text = itemView.findViewById(R.id.titleText);
        description = itemView.findViewById(R.id.description);
        pic = itemView.findViewById(R.id.pic);

    }
}
