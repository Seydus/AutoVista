package com.example.autovista.ui.adapter_and_viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class GenericAdapter extends RecyclerView.Adapter<GenericViewHolder<Map<String, Object>>> {

    private List<Map<String, Object>> buttonDataList;
    private int layoutResourceId;
    private int[] viewIds;

    public GenericAdapter(List<Map<String, Object>> buttonDataList, int layoutResourceId, int... viewIds) {
        this.buttonDataList = buttonDataList;
        this.layoutResourceId = layoutResourceId;
        this.viewIds = viewIds;
    }

    @NonNull
    @Override
    public GenericViewHolder<Map<String, Object>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
        return new GenericViewHolder<>(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder<Map<String, Object>> holder, int position) {
        Map<String, Object> buttonData = buttonDataList.get(position);
        holder.bind(buttonData, viewIds);
    }

    @Override
    public int getItemCount() {
        return buttonDataList.size();
    }
}