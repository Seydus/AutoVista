package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FragmentListOfCars extends Fragment {
    Button btnMakeModel, btnVehicleCategory;
    String model;

    public FragmentListOfCars(String model)
    {
        this.model = model;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBarBackButton();
    }

    void ActionBarBackButton()
    {
        ImageButton actionBarBackBtn = requireActivity().findViewById(R.id.backBtn);
        actionBarBackBtn.setVisibility(View.VISIBLE);

        TextView titleTxt = requireActivity().findViewById(R.id.titleTxt);
        titleTxt.setText("Listings");

        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new FragmentVehicleModel())
                        .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);

        List<Map<String, Object>> dataList = new ArrayList<>();

        List<DocumentSnapshot> items = GlobalManager.Instance.getCarHandler().RetrieveCarBrandModelData(model);

        for (DocumentSnapshot document : items) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GlobalManager.Instance.getMainActivity().GoNextModelInformationPage(document.getId());
                }
            };

            Map<String, Object> data = new HashMap<>();
            data.put("clickListener", onClickListener);
            // Add more parameters as needed

            Map<String, Object> carInfoData = new HashMap<>();
            data.put(String.valueOf(R.id.carName), document.getId());
            data.put(String.valueOf(R.id.carPrice), Objects.requireNonNull(document.get("Price")).toString() + " ₱");

            dataList.add(data);
        }

        int layoutResourceId = R.layout.car_information_item;

        GenericAdapter adapter = new GenericAdapter(dataList, layoutResourceId,
                R.id.car_information_item_framelayout,
                R.id.carName,
                R.id.carPrice,
                R.id.carType);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }
}
