package com.example.autovista.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.authentication.AuthenticationCallback;
import com.example.autovista.models.user.User;

public class FragmentSignUp extends Fragment {
    Button goBackToLoginBtn;
    Button registerBtn;
    TextView emailText;
    TextView passwordText;
    TextView confirmPasswordText;
    TextView firstNameText;
    TextView lastNameText;
    ProgressDialog dialog;

    private void showToast(String message) {
        requireContext();
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        dialog = new ProgressDialog(requireActivity());
        dialog.setCancelable(false);
        goBackToLoginBtn = view.findViewById(R.id.register_login_btn);

        registerBtn = view.findViewById(R.id.btn_register);
        emailText = view.findViewById(R.id.email);
        passwordText = view.findViewById(R.id.password);
        confirmPasswordText = view.findViewById(R.id.confirmPassword);
        firstNameText = view.findViewById(R.id.firstName);
        lastNameText = view.findViewById(R.id.lastName);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches())
                {
                    showToast("Input a valid email!");
                    return;
                }

                if (TextUtils.isEmpty(emailText.getText().toString()) ||
                    TextUtils.isEmpty(passwordText.getText().toString()) ||
                    TextUtils.isEmpty(firstNameText.getText().toString()) ||
                    TextUtils.isEmpty(lastNameText.getText().toString()))
                {
                    showToast("Fill up all inputs!");
                    return;
                }

                if(!passwordText.getText().toString().equals(confirmPasswordText.getText().toString()))
                {
                    showToast("Password does not match!");
                    return;
                }

                if(passwordText.getText().toString().length() < 8)
                {
                    showToast("Password is not too strong. Min. of 8 characters");
                    return;
                }

                User user = new User();

                user.setEmail(emailText.getText().toString());
                user.setPassword(passwordText.getText().toString());
                user.setFirstName(firstNameText.getText().toString());
                user.setLastName(lastNameText.getText().toString());

                dialog.setTitle("Registering...");
                dialog.show();

                GlobalManager.Instance.getFirebaseAuthentication().register(user, new AuthenticationCallback() {
                    @Override
                    public void OnCallback(boolean state) {
                        dialog.dismiss();

                        if(state)
                        {
                            showToast("User registered!");
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.mainFrameLayout, new FragmentLogin())
                                    .commit();
                        }
                        else
                        {
                            showToast("User already exist!");
                        }
                    }
                });
            }
        });

        goBackToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout, new FragmentLogin())
                        .commit();
            }
        });


        return view;
    }

    private boolean isStrongPassword(String password) {
        // Define the criteria for a strong password
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        // Use the pattern to check the password
        return password.matches(passwordPattern);
    }
}
