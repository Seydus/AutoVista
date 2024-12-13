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

        // Initialize adapter with click listener
        modelAdapter = new ModelAdapter(modelList, carModel -> {
            if (getActivity() != null) {
                FragmentModelDetails fragment = FragmentModelDetails.newInstance(carModel);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment) // Replace with your container ID
                        .addToBackStack(null) // Allow navigation back
                        .commit();
            }
        });

        modelsRecyclerView.setAdapter(modelAdapter);

        // Fetch models for the selected brand
        fetchModels();

        return view;
    }

    private void fetchModels() {
        firestoreHelper.fetchCarModels(brand, new FirestoreHelper.FirestoreCallback() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof List) {
                    List<Map<String, Object>> models = (List<Map<String, Object>>) data;
                    if (models.isEmpty()) {
                        Log.e("FragmentModels", "No models found for brand: " + brand);
                    } else {
                        Log.d("FragmentModels", "Models fetched: " + models);
                        modelList.clear();

                        for (int i = 0; i < models.size(); i++) {
                            Map<String, Object> modelDetails = models.get(i);
                            String modelName = null;

                            try {
                                // Retrieve modelName from the document ID or the modelDetails map
                                if (modelDetails.containsKey("documentId")) {
                                    modelName = (String) modelDetails.get("documentId");
                                } else {
                                    modelName = "Unknown Model";
                                }

                                // Parse other details
                                String interiorColor = (String) modelDetails.getOrDefault("InteriorColor", "Unknown");
                                String exteriorColor = (String) modelDetails.getOrDefault("ExteriorColor", "Unknown");
                                String fuelType = (String) modelDetails.getOrDefault("FuelType", "Unknown");
                                String transmission = (String) modelDetails.getOrDefault("Transmission", "Unknown");
                                String vin = (String) modelDetails.getOrDefault("VIN", "Unknown");
                                int mileage = ((Number) modelDetails.getOrDefault("Mileage", 0)).intValue();
                                int weight = ((Number) modelDetails.getOrDefault("Weight", 0)).intValue();
                                int price = ((Number) modelDetails.getOrDefault("Price", 0)).intValue();

                                // Create CarModel object
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
                            } catch (Exception e) {
                                Log.e("FragmentModels", "Error parsing model data: " + modelDetails, e);
                            }
                        }
                        modelAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("FragmentModels", "Unexpected data format: " + data);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("FragmentModels", "Failed to fetch models for brand: " + brand, e);
            }
        });
    }

}

