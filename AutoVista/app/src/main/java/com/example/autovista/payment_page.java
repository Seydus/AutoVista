package com.example.autovista;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class payment_page extends AppCompatActivity {

    // UI Components
    private EditText ccNameEt;       // EditText for "Name on Credit Card" (Dummy Input)
    private EditText ccNumberEt;     // EditText for "Credit Card Number"
    private EditText ccExpiryEt;     // EditText for "MM/YY" (Dummy Input)
    private EditText ccCvvEt;        // EditText for "CVV" (Dummy Input)
    private LinearLayout proceedButton; // Button to proceed to confirmation page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure your XML file is named correctly (e.g., activity_payment_page.xml)
        setContentView(R.layout.activity_payment_page);

        // Bind UI Components
        // bindUIComponents();

        // Set Proceed Button Click Listener
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleProceed();
            }
        });

        // Optional: Add TextWatchers to format credit card number as user types
        addTextWatchers();
    }

    /**
     * Binds all the UI components to their corresponding views in the XML layout.
     */
    /*
    private void bindUIComponents() {
        ccNameEt = findViewById(R.id.cc_name_et);
        ccNumberEt = findViewById(R.id.cc_number_et);
        ccExpiryEt = findViewById(R.id.cc_expiry_et);
        ccCvvEt = findViewById(R.id.cc_cvv_et);
        proceedButton = findViewById(R.id.proceed_fakebtn);
    }
    */

    /**
     * Handles the Proceed button click event.
     */
    private void handleProceed() {
        String ccNumber = ccNumberEt.getText().toString().trim();

        if (ccNumber.isEmpty()) {
            Toast.makeText(this, "Please enter your credit card number.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidCreditCardNumber(ccNumber)) {
            Toast.makeText(this, "Please enter a valid credit card number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mask the credit card number to show only the last two digits
        String maskedCC = maskCreditCardNumber(ccNumber);

        // Proceed to confirmation_page with masked credit card number
        Intent intent = new Intent(payment_page.this, confirmation_page.class);
        intent.putExtra("MASKED_CC_NUMBER", maskedCC);

        // Optionally, pass other necessary data like CAR_ID
        // For example, if you have a selectedCarId from previous activity:
        // intent.putExtra("CAR_ID", selectedCarId);

        startActivity(intent);
        finish(); // Finish the current activity to prevent returning with the back button
    }

    /**
     * Masks the credit card number to display only the last two digits.
     * Example: "5555 1234 8302 1065" becomes "**** **** **06 65"
     *
     * @param ccNumber The original credit card number.
     * @return The masked credit card number.
     */
    private String maskCreditCardNumber(String ccNumber) {
        // Remove any non-digit characters
        String digitsOnly = ccNumber.replaceAll("\\D", "");

        if (digitsOnly.length() < 2) {
            return "**** **** **" + digitsOnly; // Not enough digits to mask
        }

        String lastTwoDigits = digitsOnly.substring(digitsOnly.length() - 2);

        // Build masked string
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < digitsOnly.length() - 2; i++) {
            if (i > 0 && i % 4 == 0) {
                masked.append(" ");
            }
            masked.append("*");
        }

        // Ensure spacing aligns with user input formatting
        masked.append(" ").append(lastTwoDigits.substring(0, 2));

        return masked.toString();
    }

    /**
     * Validates the credit card number format.
     * This is a simple validation that checks if the number consists of 13 to 19 digits.
     *
     * @param ccNumber The credit card number to validate.
     * @return True if valid, false otherwise.
     */
    private boolean isValidCreditCardNumber(String ccNumber) {
        String digitsOnly = ccNumber.replaceAll("\\D", "");
        return digitsOnly.length() >= 13 && digitsOnly.length() <= 19;
    }

    /**
     * Adds TextWatchers to format the credit card number as the user types.
     * This enhances user experience by adding spaces after every 4 digits.
     */
    private void addTextWatchers() {
        ccNumberEt.addTextChangedListener(new TextWatcher() {
            boolean isFormatting;
            boolean deletingSpace;
            int spaceStart;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count > 0 && after == 0) { // Deleting
                    if (s.charAt(start) == ' ') {
                        deletingSpace = true;
                        spaceStart = start;
                    } else {
                        deletingSpace = false;
                    }
                } else {
                    deletingSpace = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed here
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;

                isFormatting = true;

                StringBuilder sb = new StringBuilder();
                String digitsOnly = s.toString().replaceAll("\\D", "");

                for (int i = 0; i < digitsOnly.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        sb.append(" ");
                    }
                    sb.append(digitsOnly.charAt(i));
                }

                ccNumberEt.removeTextChangedListener(this);
                ccNumberEt.setText(sb.toString());
                ccNumberEt.setSelection(sb.length());
                ccNumberEt.addTextChangedListener(this);

                isFormatting = false;
            }
        });
    }
}