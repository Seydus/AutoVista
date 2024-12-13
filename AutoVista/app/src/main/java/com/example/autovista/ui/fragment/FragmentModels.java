package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.models.car.CarModel;
import com.example.autovista.remotedatabase.FirestoreHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentModels extends Fragment {

    private static final String ARG_BRAND = "brand";
    private String brand;
    private RecyclerView modelsRecyclerView;
    private ModelAdapter modelAdapter;
    private List<CarModel> modelList = new ArrayList<>();
    private FirestoreHelper firestoreHelper;

    public static FragmentModels newInstance(String brand) {
        FragmentModels fragment = new FragmentModels();
        Bundle args = new Bundle();
        args.putString(ARG_BRAND, brand);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            brand = getArguments().getString(ARG_BRAND);
        }
        firestoreHelper = new FirestoreHelper();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_models, container, false);

        // Setup RecyclerView for models
        modelsRecyclerView = view.findViewById(R.id.models_recycler_view);
        modelsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modelAdapter = new ModelAdapter(modelList);
        modelsRecyclerView.setAdapter(modelAdapter);

        // Fetch models for the selected brand
        fetchModels();

        return view;
    }

    private void fetchModels() {
        firestoreHelper.fetchCarModels(brand, new FirestoreHelper.FirestoreCallback() {
            @Override
            public void onSuccess(Object data) {
                modelList.clear();
                List<Map<String, Object>> models = (List<Map<String, Object>>) data;
                for (Map<String, Object> modelDetails : models) {
                    String modelName = (String) modelDetails.get("modelName");
                    String exteriorColor = (String) modelDetails.get("ExteriorColor");
                    String fuelType = (String) modelDetails.get("FuelType");
                    String interiorColor = (String) modelDetails.get("InteriorColor");
                    int mileage = ((Number) modelDetails.get("Mileage")).intValue();
                    int price = ((Number) modelDetails.get("Price")).intValue();
                    String transmission = (String) modelDetails.get("Transmission");
                    String vin = (String) modelDetails.get("VIN");
                    int weight = ((Number) modelDetails.get("Weight")).intValue();

                    CarModel carModel = new CarModel(
                            modelName,
                            brand,
                            exteriorColor,
                            fuelType,
                            interiorColor,
                            mileage,
                            price,
                            transmission,
                            vin,
                            weight
                    );
                    modelList.add(carModel);
                }
                modelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("FragmentModels", "Failed to fetch models for brand: " + brand, e);
            }
        });
    }
}

