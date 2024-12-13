package com.example.autovista.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.models.car.CarModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class FragmentModelDetails extends Fragment {

    private static final String ARG_MODEL = "model";
    private CarModel carModel;

    public static FragmentModelDetails newInstance(CarModel carModel) {
        FragmentModelDetails fragment = new FragmentModelDetails();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MODEL, carModel); // Use Parcelable instead of Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carModel = getArguments().getParcelable(ARG_MODEL); // Retrieve Parcelable object
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model_details, container, false);

        // Initialize UI components
        ImageView carImage = view.findViewById(R.id.model_details_image);
        TextView modelName = view.findViewById(R.id.model_details_name);
        TextView brandName = view.findViewById(R.id.model_details_brand);
        TextView price = view.findViewById(R.id.model_details_price);
        TextView specs = view.findViewById(R.id.model_details_specs);
        Button buyButton = view.findViewById(R.id.model_details_buy_button);

        // Set data
        if (carModel != null) {
            modelName.setText(carModel.getModelName());
            brandName.setText(carModel.getBrand());
            price.setText("Price: $" + carModel.getPrice());
            specs.setText(
                    "Exterior Color: " + carModel.getExteriorColor() + "\n" +
                            "Interior Color: " + carModel.getInteriorColor() + "\n" +
                            "Fuel Type: " + carModel.getFuelType() + "\n" +
                            "Transmission: " + carModel.getTransmission() + "\n" +
                            "Mileage: " + carModel.getMileage() + " km\n" +
                            "Weight: " + carModel.getWeight() + " kg\n" +
                            "VIN: " + carModel.getVin()
            );

            // Load image dynamically
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference imageRef = storage.getReference().child(carModel.getStoragePath());

            imageRef.getDownloadUrl()
                    .addOnSuccessListener(uri -> {
                        new Thread(() -> {
                            try {
                                java.net.URL url = new java.net.URL(uri.toString());
                                final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                if (bitmap != null) {
                                    getActivity().runOnUiThread(() -> carImage.setImageBitmap(bitmap));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    })
                    .addOnFailureListener(e -> Log.e("Firebase Storage", "Image not found at path: " + carModel.getStoragePath(), e));
        }

        // Handle Buy button click
        buyButton.setOnClickListener(v -> {
            Log.d("FragmentModelDetails", "Buy button clicked for model: " + carModel.getModelName());
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:09154289822"));
            startActivity(intent);
            GlobalManager.Instance.setSelected(false);
            GlobalManager.Instance.setSelectedInformation(false);
        });

        return view;
    }
}
