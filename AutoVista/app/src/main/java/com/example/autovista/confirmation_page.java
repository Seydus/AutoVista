package com.example.autovista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovista.models.car.CarInfo;
import com.example.autovista.models.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class confirmation_page extends AppCompatActivity {

    // UI Components
    private TextView carNameTv;
    private TextView carColorTv;
    private TextView carPriceTv;
    private TextView fullnameTv;
    private TextView creditCardNumTv;
    private TextView subtotalTv;
    private TextView subtotalActualTv;
    private TextView feesTv;
    private TextView feesActualTv;
    private TextView totalTv;
    private TextView totalActualTv;
    private Button confirmButton;
    private ImageView carImageView;

    // Firebase References
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    // User and Car IDs
    private String userId; // Obtained from FirebaseAuth
    private String carId;  // Obtained from Intent extras

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Bind UI Components
        bindUIComponents();

        // Retrieve carId from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CAR_ID")) {
            carId = intent.getStringExtra("CAR_ID");
        } else {
            Toast.makeText(this, "No Car ID provided.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no carId is provided
            return;
        }

        // Set Confirm Button Click Listener
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleConfirm();
            }
        });
    }

    /**
     * Binds all the UI components to their corresponding views in the XML layout.
     */
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

    /**
     * Fetches the user data from Firebase Realtime Database and populates the UI.
     */
    private void fetchUserData() {
        databaseReference.child("users").child(userId).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(confirmation_page.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
                return;
            }

            DataSnapshot dataSnapshot = task.getResult();
            if (dataSnapshot.exists()) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    populateUserData(user);
                }
            } else {
                Toast.makeText(confirmation_page.this, "User data does not exist.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Populates the user-related UI components.
     *
     * @param user The User object containing user data.
     */
    private void populateUserData(User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        fullnameTv.setText(fullName);
        // The "Name on Credit Card" is a dummy input, so we don't use it here.
    }

    /**
     * Fetches the car data from Firebase Realtime Database and populates the UI.
     */
    private void fetchCarData() {
        databaseReference.child("cars").child(carId).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(confirmation_page.this, "Error fetching car data.", Toast.LENGTH_SHORT).show();
                return;
            }

            DataSnapshot dataSnapshot = task.getResult();
            if (dataSnapshot.exists()) {
                CarInfo carInfo = dataSnapshot.getValue(CarInfo.class);
                if (carInfo != null) {
                    populateCarData(carInfo);
                }
            } else {
                Toast.makeText(confirmation_page.this, "Car data does not exist.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Populates the car-related UI components.
     *
     * @param carInfo The CarInfo object containing car data.
     */
    private void populateCarData(CarInfo carInfo) {
        carNameTv.setText(carInfo.getMake() + " " + carInfo.getModel());
        carColorTv.setText(carInfo.getColor());
        carPriceTv.setText("$" + String.format("%.2f", carInfo.getPrice()));
        subtotalTv.setText("Subtotal");
        subtotalActualTv.setText("$" + String.format("%.2f", carInfo.getPrice()));
        feesTv.setText("incl. fees");
        feesActualTv.setText("$0.00"); // Static value
        totalTv.setText("Total");
        totalActualTv.setText("$" + String.format("%.2f", carInfo.getPrice())); // Assuming total equals price + fees

        // Receive masked credit card number from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("MASKED_CC_NUMBER")) {
            String maskedCC = intent.getStringExtra("MASKED_CC_NUMBER");
            creditCardNumTv.setText(maskedCC);
        } else {
            creditCardNumTv.setText("**** **** **83"); // Default or fallback masked number
        }

        // Optionally, set the car image if you have an image URL in CarInfo
        // Example using Glide library:
        /*
        if (carInfo.getImageUrl() != null && !carInfo.getImageUrl().isEmpty()) {
            Glide.with(this)
                .load(carInfo.getImageUrl())
                .placeholder(R.drawable.icon) // Default image
                .into(carImageView);
        }
        */
        // Ensure you have added the Glide dependency if you choose to use it
    }

    /**
     * Handles the confirmation button click event.
     */
    private void handleConfirm() {
        // TODO: Implement any submission logic if required, such as updating Firebase

        // Redirect to the SuccessfulTransaction activity
        Intent intent = new Intent(confirmation_page.this, SuccessfulTransaction.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent returning with the back button
    }
}