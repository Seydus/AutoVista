package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FragmentVehicleModel extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);

        List<Map<String, Object>> buttonDataList = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String buttonText = "Button " + i;
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new FragmentListOfCars())
                            .commit();

                    Toast.makeText(requireContext(), "Button Clicked: " + buttonText, Toast.LENGTH_SHORT).show();
                }
            };

            Map<String, Object> buttonData = new HashMap<>();
            buttonData.put("text", buttonText);
            buttonData.put("clickListener", onClickListener);
            // Add more parameters as needed

            buttonDataList.add(buttonData);
        }

        int layoutResourceId = R.layout.car_image_name_item;

        GenericAdapter adapter = new GenericAdapter(buttonDataList, layoutResourceId, R.id.car_item_text_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }
}