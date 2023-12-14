package com.example.autovista.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.autovista.R;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCarInformation extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBarBackButton();
    }

    void ActionBarBackButton()
    {
        ImageButton actionBarBackBtn = requireActivity().findViewById(R.id.backBtn);
        actionBarBackBtn.setVisibility(View.VISIBLE);

        ImageButton bookMarkBtn = requireActivity().findViewById(R.id.bookmark_btn);
        bookMarkBtn.setVisibility(View.VISIBLE);

        TextView titleTxt = requireActivity().findViewById(R.id.titleTxt);
        titleTxt.setText("Information");

        bookMarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use ContextCompat.getDrawable to retrieve the Drawable from the resource ID
                Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_bookmark_added_24);
                // Set the background drawable
                bookMarkBtn.setBackground(drawable);
            }
        });


        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new FragmentListOfCars())
                        .commit();

                actionBarBackBtn.setVisibility(View.GONE);
                bookMarkBtn.setVisibility(View.GONE);

                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
                bottomNavigationView.getMenu().clear();
                requireActivity().getMenuInflater().inflate(R.menu.bottom_nav, bottomNavigationView.getMenu());
            }
        });
    }

    void NavigationBarViewButton()
    {
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.getMenu().clear();
        requireActivity().getMenuInflater().inflate(R.menu.car_information_nav, bottomNavigationView.getMenu());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        NavigationBarViewButton();

        View view = inflater.inflate(R.layout.fragment_car_information, container, false);

        TextView carNameData = view.findViewById(R.id.carNameData);
        TextView carTypeData = view.findViewById(R.id.carTypeData);
        TextView carPriceData = view.findViewById(R.id.carPriceData);
        TextView carEngineData = view.findViewById(R.id.engineData);
        TextView carTransmissionData = view.findViewById(R.id.transmissionData);
        TextView carVinData = view.findViewById(R.id.vinData);
        TextView carDriveTypeData = view.findViewById(R.id.driveTypeData);
        TextView carExteriorColorData = view.findViewById(R.id.exteriorColorData);
        TextView carInteriorColorData = view.findViewById(R.id.interiorColorData);
        TextView carStockNumber = view.findViewById(R.id.stockData);

        carNameData.setText("Honda");
        carTypeData.setText("Sedan");

        carPriceData.setText("$37,000");
        carExteriorColorData.setText("Blue");
        carInteriorColorData.setText("Violet");
        carEngineData.setText("Gas");
        carDriveTypeData.setText("Music");
        carTransmissionData.setText("Manual");
        carVinData.setText("23599103135");
        carStockNumber.setText("10");

        return view;
    }
}