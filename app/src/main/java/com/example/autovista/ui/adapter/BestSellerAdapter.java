package com.example.autovista.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.models.car.Car;

import java.util.List;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerViewHolder> {
    private List<Car> bestSellers;
    private Context context;

    public BestSellerAdapter(List<Car> bestSellers, Context context) {
        this.bestSellers = bestSellers;
        this.context = context;
    }

    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = (LayoutInflater.from(parent.getContext()).inflate(R.layout.best_sellers, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, int position) {
//        holder.title_text.setText();
//        holder.description.setText();

    }

    @Override
    public int getItemCount() {
        return bestSellers.size();
    }
}
