package com.example.autovista.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.models.car.Car;
import com.example.autovista.ui.adapter.CarViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarViewHolder> {

    private Context context;
    private List<Car> carList = new ArrayList<>();

    public CarAdapter(List<Car> carList, Context context) {
        this.context = context;
        this.carList = carList;
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new CarViewHolder(LayoutInflater.from(context).inflate(R.layout.car_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        // holder.text_CarItem.setText(carList.get(position).getId().get(0).getMain());
    }
}