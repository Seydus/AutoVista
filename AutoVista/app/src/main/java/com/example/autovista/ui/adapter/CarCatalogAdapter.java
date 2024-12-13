package com.example.autovista.ui.adapter;

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

public class CarCatalogAdapter extends RecyclerView.Adapter<CarCatalogAdapter.ViewHolder> {

    private List<CarModel> carModelList;

    public CarCatalogAdapter(List<CarModel> carModelList) {
        this.carModelList = carModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return carModelList.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarModel carModel = carModelList.get(position);

        // Set text fields
        holder.tvBrandName.setText(carModel.getBrand());
        holder.tvModelName.setText(carModel.getModelName());
        //holder.tv.setText("Details: " + carModel.getExteriorColor() + ", " + carModel.getFuelType() + ", " + carModel.getTransmission());

        // Load image dynamically
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(carModel.getStoragePath());

        imageRef.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    new Thread(() -> {
                        Bitmap bitmap = loadImageFromUrl(uri.toString());
                        if (bitmap != null) {
                            holder.itemView.post(() -> holder.carImage.setImageBitmap(bitmap));
                        }
                    }).start();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase Storage", "Image not found at path: " + carModel.getStoragePath(), e);
                    // Display placeholder image
                    holder.carImage.setImageResource(R.drawable.icon);
                });

    }

    private Bitmap loadImageFromUrl(String urlString) {
        try {
            java.net.URL url = new java.net.URL(urlString);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView carImage;
        TextView tvModelName, tvBrandName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.catalog_car_image);
            tvModelName = itemView.findViewById(R.id.tvModel);
            tvBrandName = itemView.findViewById(R.id.tvBrand);
        }
    }
}
