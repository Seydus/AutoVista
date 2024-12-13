package com.example.autovista.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.models.car.CarModel;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    private List<CarModel> models;

    public ModelAdapter(List<CarModel> models) {
        this.models = models;
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
        holder.modelName.setText(carModel.getModelName());
        holder.brandName.setText(carModel.getBrand());
        holder.price.setText("Price: " + carModel.getPrice());

        // Load image from Firebase Storage
        //FirebaseStorage.getInstance().getReference().child(carModel.getStoragePath()).getDownloadUrl()
          //      .addOnSuccessListener(uri -> Glide.with(holder.itemView.getContext()).load(uri).into(holder.image))
            //    .addOnFailureListener(e -> holder.image.setImageResource(R.drawable.placeholder_image));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView modelName, brandName, price;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelName = itemView.findViewById(R.id.model_name);
            brandName = itemView.findViewById(R.id.brand_name);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.model_image);
        }
    }
}

