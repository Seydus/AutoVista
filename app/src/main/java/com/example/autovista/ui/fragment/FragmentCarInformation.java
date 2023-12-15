package com.example.autovista.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class FragmentCarInformation extends Fragment {

    String brand;

    public FragmentCarInformation(String brand)
    {
        this.brand = brand;
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
                TextView carNameData = requireActivity().findViewById(R.id.carNameData);
                GlobalManager.Instance.setSelectedInformation(false);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new FragmentListOfCars(brand))
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
        DocumentSnapshot item = GlobalManager.Instance.getCarHandler().RetriveCarInfo(brand);

        TextView carNameData = view.findViewById(R.id.carNameData);
        TextView carPriceData = view.findViewById(R.id.carPriceData);
        TextView carFuelTypeData = view.findViewById(R.id.fuelTypeData);
        TextView carTransmissionData = view.findViewById(R.id.transmissionData);
        TextView carVinData = view.findViewById(R.id.vinData);
        TextView carExteriorColorData = view.findViewById(R.id.exteriorColorData);
        TextView carInteriorColorData = view.findViewById(R.id.interiorColorData);
        TextView carWeightData = view.findViewById(R.id.weightData);
        TextView carMileageData = view.findViewById(R.id.mileageData);

        carNameData.setText(brand);

        carPriceData.setText("132400 ₱");
        carExteriorColorData.setText("Pearl White");
        carInteriorColorData.setText("Wine Red");
        carFuelTypeData.setText("Gasoline");
        carTransmissionData.setText("Automatic");
        carVinData.setText("WAUAA88G5VN001878");
        carWeightData.setText("1770");
        carMileageData.setText("1120");

        return view;
    }
}