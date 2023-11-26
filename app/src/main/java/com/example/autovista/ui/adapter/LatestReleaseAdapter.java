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

public class LatestReleaseAdapter extends RecyclerView.Adapter<LatestReleaseViewHolder> {

    private List<Car> latestReleases;
    private Context context;

    public LatestReleaseAdapter(List<Car> latestReleases, Context context) {
        this.latestReleases = latestReleases;
        this.context = context;
    }

    @NonNull
    @Override
    public LatestReleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = (LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_releases, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LatestReleaseViewHolder holder, int position) {
//        holder.title_text.setText();
//        holder.description.setText();
    }

    @Override
    public int getItemCount() {
        return latestReleases.size();
    }
}
