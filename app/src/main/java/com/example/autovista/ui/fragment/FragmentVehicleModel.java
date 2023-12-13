package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FragmentVehicleModel extends Fragment {

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
        titleTxt.setText("Models");

        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new FragmentHome())
                        .commit();

                actionBarBackBtn.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);

        List<Map<String, Object>> dataList = new ArrayList<>();

        String[] carMakes = {
                "Audi", "BMW", "Chevrolet", "Ford", "Honda",
                "Hyundai", "Jaguar", "Kia", "Mazda", "Mercedes-Benz",
                "Nissan", "Subaru", "Tesla", "Toyota", "Volkswagen"
        };


        for (int i = 0; i < carMakes.length; i++) {
            String buttonText = carMakes[i];
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new FragmentListOfCars())
                            .commit();
                }
            };

            Map<String, Object> data = new HashMap<>();
            data.put(String.valueOf(R.id.car_item_text_button), buttonText);
            data.put("clickListener", onClickListener);
            // Add more parameters as needed

            dataList.add(data);
        }

        int layoutResourceId = R.layout.car_image_name_item;

        GenericAdapter adapter = new GenericAdapter(dataList, layoutResourceId, R.id.car_item_text_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }
}