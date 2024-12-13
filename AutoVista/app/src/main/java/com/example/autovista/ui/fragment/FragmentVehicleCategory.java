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
import com.example.autovista.remotedatabase.FirestoreHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentVehicleCategory extends Fragment {

    private RecyclerView brandRecyclerView;
    private BrandAdapter brandAdapter;
    private List<String> brandList = new ArrayList<>();
    private FirestoreHelper firestoreHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicle_category, container, false);

        // Initialize FirestoreHelper
        firestoreHelper = new FirestoreHelper();

        // Setup RecyclerView for brands
        brandRecyclerView = view.findViewById(R.id.brand_recycler_view);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter and set it to the RecyclerView
        brandAdapter = new BrandAdapter(brandList, brand -> {
            if (getActivity() != null) {
                // On brand click, navigate to models fragment
                FragmentModels fragmentModels = FragmentModels.newInstance(brand);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragmentModels)
                        .addToBackStack(null) // Optional: Allow navigating back
                        .commit();
            } else {
                Log.e("FragmentVehicleCategory", "getActivity() returned null");
            }
        });
        brandRecyclerView.setAdapter(brandAdapter);

        // Fetch brands from Firestore
        fetchBrands();

        return view;
    }

    private void fetchBrands() {
        firestoreHelper.fetchCarBrands(new FirestoreHelper.FirestoreCallback() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof List) {
                    List<String> fetchedBrands = (List<String>) data;
                    if (fetchedBrands.isEmpty()) {
                        Log.e("FragmentVehicleCategory", "No brands found in Firestore.");
                    } else {
                        Log.d("FragmentVehicleCategory", "Fetched brands: " + fetchedBrands);
                        brandList.clear();
                        brandList.addAll(fetchedBrands);
                        brandAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("FragmentVehicleCategory", "Unexpected data type received: " + data);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("FragmentVehicleCategory", "Failed to fetch brands from Firestore", e);
            }
        });
    }
}
