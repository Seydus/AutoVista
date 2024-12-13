package com.example.autovista.ui.adapter_and_viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.models.CatalogItem;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<CatalogItem> carList;

    public RecommendedAdapter(Context context, List<CatalogItem> carList) {
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_catalog_item_recommended, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        CatalogItem car = carList.get(position);
        holder.carImage.setImageResource(car.getImageResId());
        holder.carModel.setText(car.getModelName());
        holder.carBrand.setText(car.getBrand());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    static class RecommendedViewHolder extends RecyclerView.ViewHolder {
        ImageView carImage;
        TextView carModel, carBrand;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.recommended_car_image);
            carModel = itemView.findViewById(R.id.recommended_car_model);
            carBrand = itemView.findViewById(R.id.recommended_car_brand);
        }
    }
}
