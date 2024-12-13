package com.example.autovista.ui.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.models.car.CarItemsProfile;
import com.example.autovista.remotedatabase.FirestoreDataCallback;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FragmentListOfCars extends Fragment {
    Button btnMakeModel, btnVehicleCategory;
    CarItemsProfile profile;

    public FragmentListOfCars(CarItemsProfile profile) {
        this.profile = profile;
    }

    private void showToast(String message) {
        requireContext();
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarBackButton();
    }

    void ActionBarBackButton() {
        ImageButton actionBarBackBtn = requireActivity().findViewById(R.id.backBtn);
        actionBarBackBtn.setVisibility(View.GONE);

        TextView titleTxt = requireActivity().findViewById(R.id.titleTxt);
        titleTxt.setText("Listings");

        actionBarBackBtn.setOnClickListener(v -> {
            int entryCount = requireActivity().getSupportFragmentManager().getBackStackEntryCount();
            Log.d("BackStack", "Back stack entry count: " + entryCount);
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);
        FilterComponent filterComponent = view.findViewById(R.id.filterComponent);
        List<String> models = new ArrayList<>();
        List<String> brands = new ArrayList<>();
        for (DocumentSnapshot modelItem : profile.getModelItem()) {
            models.add(modelItem.getId());
            brands.add(profile.getBrand());  // Assuming the brand is constant
        }

        filterComponent.setModelOptions(models);
        filterComponent.setBrandOptions(brands);

        // Set up search button click listener
        filterComponent.getSearchButton().setOnClickListener(v -> {
            String selectedModel = filterComponent.getSelectedModel();
            String selectedBrand = filterComponent.getSelectedBrand();
            String maxPrice = filterComponent.getMaxPrice();

            // Use the filter values to perform the search or filter your data
            filterCars(selectedModel, selectedBrand, maxPrice);
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);

        List<Map<String, Object>> dataList = new ArrayList<>();
        for (DocumentSnapshot modelItem : profile.getModelItem()) {
            String brandPath = capitalizeFirstLetter(profile.getBrand());
            String completePath = brandPath + "/" + brandPath + "-" + modelItem.getId() + ".png";

            Locale philippinesLocale = new Locale("fil", "PH");
            NumberFormat philippinePesoFormat = NumberFormat.getCurrencyInstance(philippinesLocale);
            String formattedCurrency = philippinePesoFormat.format(modelItem.get("Price"));

            Map<String, Object> data = new HashMap<>();
            data.put(String.valueOf(R.id.header_image), completePath);
            data.put(String.valueOf(R.id.carName), modelItem.getId());
            data.put(String.valueOf(R.id.carPrice), formattedCurrency);
            View.OnClickListener onClickListener = v -> {
                ProgressDialog dialog = new ProgressDialog(requireContext());
                dialog.setCancelable(false);
                dialog.setTitle("Fetching data...");
                dialog.show();

                GlobalManager.Instance.getFirestoreHelper().OnReadCarInfoFirestore(profile.getBrand(), modelItem.getId(), new FirestoreDataCallback() {
                    DocumentSnapshot carInfo;

                    @Override
                    public void onCallback(Object data) {
                        carInfo = (DocumentSnapshot) data;
                    }

                    @Override
                    public void onState(boolean state) {
                        dialog.dismiss();

                        if (state && isAdded()) {
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayout, new FragmentCarInformation(profile.getBrand(), carInfo))
                                    .addToBackStack("carInfo")
                                    .commit();
                        } else {
                            showToast("Failed to fetch data.");
                        }
                    }
                });
            };

            data.put("clickListener", onClickListener);
            dataList.add(data);
        }
        int layoutResourceId = R.layout.car_information_item;
        GenericAdapter adapter = new GenericAdapter(dataList, layoutResourceId,
                R.id.car_information_item_framelayout,
                R.id.carName,
                R.id.carPrice,
                R.id.carType,
                R.id.header_image);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void filterCars(String model, String brand, String maxPrice) {
        List<Map<String, Object>> filteredDataList = new ArrayList<>();

        for (DocumentSnapshot modelItem : profile.getModelItem()) {
            String itemModel = modelItem.getId();
            String itemBrand = profile.getBrand();
            String itemPrice = modelItem.getString("Price");

            boolean matchesModel = model.equals("") || itemModel.equals(model);
            boolean matchesBrand = brand.equals("") || itemBrand.equals(brand);
            boolean matchesPrice = maxPrice.equals("") || Integer.parseInt(itemPrice) <= Integer.parseInt(maxPrice);

            if (matchesModel && matchesBrand && matchesPrice) {
                String brandPath = capitalizeFirstLetter(profile.getBrand());
                String completePath = brandPath + "/" + brandPath + "-" + itemModel + ".png";

                Locale philippinesLocale = new Locale("fil", "PH");
                NumberFormat philippinePesoFormat = NumberFormat.getCurrencyInstance(philippinesLocale);
                String formattedCurrency = philippinePesoFormat.format(itemPrice);

                Map<String, Object> data = new HashMap<>();
                data.put(String.valueOf(R.id.header_image), completePath);
                data.put(String.valueOf(R.id.carName), itemModel);
                data.put(String.valueOf(R.id.carPrice), formattedCurrency);

                filteredDataList.add(data);
            }
        }

        GenericAdapter filteredAdapter = new GenericAdapter(filteredDataList, R.layout.car_information_item,
                R.id.car_information_item_framelayout,
                R.id.carName,
                R.id.carPrice,
                R.id.carType,
                R.id.header_image);

        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_view_list);
        recyclerView.setAdapter(filteredAdapter);
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        } else {
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }
}

