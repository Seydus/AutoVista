package com.example.autovista.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.models.car.CarModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    private List<CarModel> models;
    private OnModelClickListener onModelClickListener;

    // Constructor with click listener
    public ModelAdapter(List<CarModel> models, OnModelClickListener onModelClickListener) {
        this.models = models;
        this.onModelClickListener = onModelClickListener;
    }

    public interface OnModelClickListener {
        void onModelClick(CarModel carModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarModel carModel = models.get(position);

        // Bind data to views
        holder.modelName.setText(carModel.getModelName());
        holder.brandName.setText(carModel.getBrand());
        holder.price.setText("Price: $" + carModel.getPrice());

        // Handle item click
        holder.itemView.setOnClickListener(v -> onModelClickListener.onModelClick(carModel));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView carImage;
        TextView modelName, brandName, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelName = itemView.findViewById(R.id.model_name);
            brandName = itemView.findViewById(R.id.brand_name);
            price = itemView.findViewById(R.id.model_price);
        }
    }
}
