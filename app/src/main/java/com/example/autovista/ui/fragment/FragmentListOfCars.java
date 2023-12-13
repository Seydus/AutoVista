package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentListOfCars extends Fragment {
    Button btnMakeModel, btnVehicleCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);

        List<Map<String, Object>> frameLayoutDataList = new ArrayList<>();
        List<Map<String, Object>> carInfoDataList = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String carMake = "Honda";
            String carType = "Sedan";
            String carPrice = "$37,000";

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new FragmentCarInformation())
                            .commit();
                }
            };

            Map<String, Object> frameLayoutData = new HashMap<>();
            frameLayoutData.put("clickListener", onClickListener);
            // Add more parameters as needed

            Map<String, Object> carInfoData = new HashMap<>();
            carInfoData.put("carMake", carMake);
            carInfoData.put("carType", carType);
            carInfoData.put("carPrice", carPrice);

            frameLayoutDataList.add(frameLayoutData);
            carInfoDataList.add(carInfoData);
        }

        int layoutResourceId = R.layout.car_information_item;

        GenericAdapter adapter = new GenericAdapter(frameLayoutDataList, layoutResourceId, R.id.car_information_item_framelayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }
}
