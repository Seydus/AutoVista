package com.example.autovista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovista.models.car.CarModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class confirmation_page extends AppCompatActivity {

    private TextView carNameTv, carColorTv, carPriceTv, fullnameTv, creditCardNumTv, subtotalTv, subtotalActualTv, feesTv, feesActualTv, totalTv, totalActualTv;
    private TextView confirmButton;
    private ImageView carImageView;

    private CarModel carModel; // CarModel passed from Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        // Bind UI Components
        bindUIComponents();

        // Retrieve CarModel from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CAR_MODEL")) {
            carModel = intent.getParcelableExtra("CAR_MODEL");
        } else {
            Toast.makeText(this, "No Car Model provided.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no CarModel is provided
            return;
        }

        // Populate UI with CarModel details
        populateCarData(carModel);

        // Handle Confirm Button
        confirmButton.setOnClickListener(v -> handleConfirm());
    }

    private void bindUIComponents() {
        carNameTv = findViewById(R.id.car_name_tv);
        carColorTv = findViewById(R.id.car_color_tv);
        carPriceTv = findViewById(R.id.car_price_tv);
        fullnameTv = findViewById(R.id.fullname_tv);
        creditCardNumTv = findViewById(R.id.creditcard_num_tv);
        subtotalTv = findViewById(R.id.subtotal_tv);
        subtotalActualTv = findViewById(R.id.subtotal_actual_tv);
        feesTv = findViewById(R.id.fees_tv);
        feesActualTv = findViewById(R.id.fees_actual_tv);
        totalTv = findViewById(R.id.total_tv);
        totalActualTv = findViewById(R.id.total_actual_tv);
        confirmButton = findViewById(R.id.confirm_fakebtn);
        carImageView = findViewById(R.id.icon); // Ensure the ImageView has the correct ID
    }

    private void populateCarData(CarModel carModel) {
        carNameTv.setText(carModel.getBrand() + " " + carModel.getModelName());
        carColorTv.setText(carModel.getExteriorColor());
        carPriceTv.setText("$" + carModel.getPrice());
        subtotalActualTv.setText("$" + carModel.getPrice());
        feesActualTv.setText("$0.00"); // Static fees value
        totalActualTv.setText("$" + carModel.getPrice());

        // Load image dynamically
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(carModel.getStoragePath());
        imageRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(carImageView))
                .addOnFailureListener(e -> carImageView.setImageResource(R.drawable.icon)); // Placeholder
    }

    private void handleConfirm() {
        Toast.makeText(this, "Purchase confirmed for " + carModel.getModelName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SuccessfulTransaction.class);
        startActivity(intent);
        finish();
    }
}
