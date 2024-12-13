package com.example.autovista.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    private List<String> brands;
    private OnBrandClickListener onBrandClickListener;

    // Interface for handling brand click
    public interface OnBrandClickListener {
        void onBrandClick(String brand);
    }

    public BrandAdapter(List<String> brands, OnBrandClickListener onBrandClickListener) {
        this.brands = brands;
        this.onBrandClickListener = onBrandClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String brand = brands.get(position);
        holder.brandName.setText(brand);
        holder.itemView.setOnClickListener(v -> onBrandClickListener.onBrandClick(brand));
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView brandName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.brand_name);
        }
    }
}
