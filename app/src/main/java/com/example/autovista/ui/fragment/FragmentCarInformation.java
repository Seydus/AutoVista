package com.example.autovista.ui.fragment;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.models.car.CarItemsProfile;
import com.example.autovista.remotedatabase.FirestoreDataCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class FragmentCarInformation extends Fragment {
    DocumentSnapshot data;
    String brand;


    public FragmentCarInformation(String brand, DocumentSnapshot data)
    {
        this.data = data;
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

        TextView titleTxt = requireActivity().findViewById(R.id.titleTxt);
        titleTxt.setText(data.getId());

        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalManager.Instance.setSelectedInformation(false);

                actionBarBackBtn.setVisibility(View.GONE);

                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
                bottomNavigationView.getMenu().clear();
                requireActivity().getMenuInflater().inflate(R.menu.bottom_nav, bottomNavigationView.getMenu());

                requireActivity().getSupportFragmentManager().popBackStack();
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
        View view = inflater.inflate(R.layout.fragment_car_information, container, false);

        NavigationBarViewButton();
        UploadImage(view);

        TextView carNameData = view.findViewById(R.id.carNameData);
        TextView carPriceData = view.findViewById(R.id.carPriceData);
        TextView carFuelTypeData = view.findViewById(R.id.fuelTypeData);
        TextView carTransmissionData = view.findViewById(R.id.transmissionData);
        TextView carVinData = view.findViewById(R.id.vinData);
        TextView carExteriorColorData = view.findViewById(R.id.exteriorColorData);
        TextView carInteriorColorData = view.findViewById(R.id.interiorColorData);
        TextView carWeightData = view.findViewById(R.id.weightData);
        TextView carMileageData = view.findViewById(R.id.mileageData);

        Locale philippinesLocale = new Locale("fil", "PH");
        NumberFormat philippinePesoFormat = NumberFormat.getCurrencyInstance(philippinesLocale);
        String formattedCurrency = philippinePesoFormat.format(data.get("Price"));

        carNameData.setText(data.getId());
        carPriceData.setText(formattedCurrency);
        carExteriorColorData.setText(Objects.requireNonNull(data.get("ExteriorColor")).toString());
        carInteriorColorData.setText(Objects.requireNonNull(data.get("InteriorColor")).toString());
        carFuelTypeData.setText(Objects.requireNonNull(data.get("FuelType")).toString());
        carTransmissionData.setText(Objects.requireNonNull(data.get("Transmission")).toString());
        carVinData.setText(Objects.requireNonNull(data.get("VIN")).toString());
        carWeightData.setText(Objects.requireNonNull(data.get("Weight")).toString());
        carMileageData.setText(Objects.requireNonNull(data.get("Mileage")).toString());

        return view;
    }

    public void UploadImage(View view) {
        ImageView imageView = view.findViewById(R.id.car_information_image);

        // Get a reference to the Firebase Storage instance
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String brandPath = capitalizeFirstLetter(brand);
        String completePath = brandPath + "/" + brandPath + "-" + data.getId() + ".png";

        Log.e("IMAGELOADER", completePath);

        // Reference to the image file
        StorageReference imageRef = storageRef.child(completePath);

        try {
            File localFile = File.createTempFile("images", "png");
            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Local file has been created
                            // Set the local file as the image resource
                            imageView.setImageURI(Uri.fromFile(localFile));
                            Log.e("IMAGELOADER", "IMAGE SUCCESSFULLY LOADED");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("IMAGELOADER", "FAILED TO LOAD IMAGE: " + exception.getMessage());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IMAGELOADER", "EXCEPTION: " + e.getMessage());
        }
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return input as is for null or empty strings
        } else {
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }
}