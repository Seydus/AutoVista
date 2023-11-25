package com.example.autovista.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.models.User;
import com.google.android.material.textfield.TextInputEditText;

public class UIAuthentication {
    private Activity activity;
    private User user;

    public UIAuthentication(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
        GlobalManager.Instance.setUiAdapter(this);
    }

    public void InvokeUIAdapter() {
        OnButtonLoginUI();
        OnButtonRegisterUI();
    }

    public void InitializeViews() {
        TextInputEditText emailInput = activity.findViewById(R.id.email);
        TextInputEditText passwordInput = activity.findViewById(R.id.password);
        TextInputEditText firstNameInput = activity.findViewById(R.id.firstName);
        TextInputEditText lastNameInput = activity.findViewById(R.id.lastName);

        String email = emailInput != null ? emailInput.getText().toString() : "";
        String password = passwordInput != null ? passwordInput.getText().toString() : "";
        String firstName = firstNameInput != null ? firstNameInput.getText().toString() : "";
        String lastName = lastNameInput != null ? lastNameInput.getText().toString() : "";

        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
    }

    public void OnButtonLoginUI() {
        if (activity != null) {
            Button button = activity.findViewById(R.id.btn_login);

            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        InitializeViews();
                        GlobalManager.Instance.getAuthenticationHandler().OnLogin(user);
                    }
                });
            } else {
                Log.e("UIManager", "Button not found in the layout");
            }
        } else {
            Log.e("UIManager", "Context is null");
        }
    }

    public void OnButtonRegisterUI()
    {
        if (activity != null) {
            Button button = activity.findViewById(R.id.btn_register);

            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        InitializeViews();
                        GlobalManager.Instance.getAuthenticationHandler().OnRegister(user);
                    }
                });
            } else {
                Log.e("UIManager", "Button not found in the layout");
            }
        } else {
            Log.e("UIManager", "Context is null");
        }
    }
}
