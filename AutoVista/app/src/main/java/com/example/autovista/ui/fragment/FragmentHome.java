package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.ui.adapter.CarCatalogAdapter;
import com.example.autovista.models.car.CarModel;
import com.example.autovista.remotedatabase.FirestoreHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FragmentHome extends Fragment {

    private RecyclerView catalogRecyclerView;
    private CarCatalogAdapter adapter;
    private List<CarModel> carModelList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView catalogRecyclerView = view.findViewById(R.id.recommended_catalog_view_pager);
        catalogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        CarCatalogAdapter adapter = new CarCatalogAdapter(carModelList);
        catalogRecyclerView.setAdapter(adapter);

        TextView titleTxt = requireActivity().findViewById(R.id.titleTxt);
        titleTxt.setText("Home");

        fetchRandomBrandsAndModels(adapter);

        return view;
    }

    private void fetchRandomBrandsAndModels(CarCatalogAdapter adapter) {
        FirestoreHelper helper = new FirestoreHelper();
        helper.fetchRandomBrandsAndModels(new FirestoreHelper.FirestoreCallback() {
            @Override
            public void onSuccess(Object data) {
                Map<String, Map<String, Object>> result = (Map<String, Map<String, Object>>) data;

                for (Map.Entry<String, Map<String, Object>> entry : result.entrySet()) {
                    String brand = entry.getKey();
                    Map<String, Object> modelDetails = entry.getValue();

                    // Safely retrieve the model name and details
                    String modelName = modelDetails.get("modelName") != null ? modelDetails.get("modelName").toString() : "Unknown Model";
                    String details = modelDetails != null ? modelDetails.toString() : "Details unavailable";

                    // Log for debugging
                    Log.d("Firestore Result", "Brand: " + brand + ", Model: " + modelName + ", Details: " + details);

                    CarModel carModel = new CarModel(
                            modelName,
                            brand,
                            (String) modelDetails.getOrDefault("ExteriorColor", "Unknown Exterior"),
                            (String) modelDetails.getOrDefault("FuelType", "Unknown Fuel"),
                            (String) modelDetails.getOrDefault("InteriorColor", "Unknown Interior"),
                            ((Number) modelDetails.getOrDefault("Mileage", 0)).intValue(),
                            ((Number) modelDetails.getOrDefault("Price", 0)).intValue(),
                            (String) modelDetails.getOrDefault("Transmission", "Unknown Transmission"),
                            (String) modelDetails.getOrDefault("VIN", "Unknown VIN"),
                            ((Number) modelDetails.getOrDefault("Weight", 0)).intValue()
                    );

                    carModelList.add(carModel);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Firestore", "Failed to fetch random brands and models", e);
            }
        });
    }

    /**
     * Utility method to safely convert a numeric value to Long.
     */
    private Long getSafeLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Double) {
            return ((Double) value).longValue();
        } else {
            return 0L; // Default value in case of null or unexpected type
        }
    }
}
