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

import com.example.autovista.R;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FragmentListOfCars extends Fragment {
    Button btnMakeModel, btnVehicleCategory;

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

        for (int i = 0; i < 10; i++) {
            String[] carNames = {
                    "Audi A3", "Audi A4", "Audi A5", "Audi A6", "Audi A7", "Audi A8",
                    "Audi Q3", "Audi Q5", "Audi Q7", "Audi Q8", "Audi RS3", "Audi RS4",
                    "Audi RS5", "Audi RS6", "Audi RS7", "Audi S3", "Audi S4", "Audi S5",
                    "Audi S6", "Audi S7", "Audi S8", "Audi SQ5", "Audi SQ7", "Audi TT",
                    "Audi R8", "Audi e-Tron"
            };
            String[] carTypes = {
                    "Sedan", "SUV", "Coupe", "Convertible", "Hatchback",
                    "Wagon", "Crossover", "Roadster", "Coupe-SUV", "Sports Car",
                    "Luxury Sedan", "Compact SUV", "Midsize SUV", "Full-size SUV",
                    "Compact Car", "Midsize Car", "Full-size Car", "Electric Car", "Hybrid Car"
            };

            String[] carPrices = {
                    "$37,000", "$40,000", "$32,000", "$45,000", "$38,500",
                    "$42,000", "$34,500", "$48,000", "$36,000", "$50,000",
                    "$39,000", "$44,000", "$35,000", "$47,000", "$41,000",
                    "$55,000", "$46,000", "$52,000", "$49,000", "$60,000"
            };


            String carMake = getRandomElement(carNames);
            String carType = getRandomElement(carTypes);
            String carPrice = getRandomElement(carPrices);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new FragmentCarInformation())
                            .commit();
                }
            };

            Map<String, Object> data = new HashMap<>();
            data.put("clickListener", onClickListener);
            // Add more parameters as needed

            Map<String, Object> carInfoData = new HashMap<>();
            data.put(String.valueOf(R.id.carName), carMake);
            data.put(String.valueOf(R.id.carType), carType);
            data.put(String.valueOf(R.id.carPrice), carPrice);

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

    private static String getRandomElement(String[] array) {
        Random random = new Random();
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];
    }
}
