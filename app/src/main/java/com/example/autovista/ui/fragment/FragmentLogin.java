package com.example.autovista.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.authentication.AuthenticationCallback;
import com.example.autovista.models.user.User;

public class FragmentLogin extends Fragment {

    TextView emailText;
    TextView passwordText;
    Button loginBtn;
    Button signUpBtn;

    ProgressDialog dialog;


    private void showToast(String message) {
        requireContext();
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        signUpBtn = view.findViewById(R.id.login_signup_btn);

        emailText = view.findViewById(R.id.email);
        passwordText = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.btn_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches())
                {
                    showToast("Input a valid email!");
                    return;
                }

                if (TextUtils.isEmpty(emailText.getText().toString()) ||
                        TextUtils.isEmpty(passwordText.getText().toString()))
                {
                    showToast("Fill up all inputs!");
                    return;
                }

                dialog = new ProgressDialog(requireActivity());
                dialog.setCancelable(false);
                dialog.setTitle("Logging in");

                dialog.show();

                User user = new User();
                user.setEmail(emailText.getText().toString());
                user.setPassword(passwordText.getText().toString());

                GlobalManager.Instance.getFirebaseAuthentication().logIn(user, new AuthenticationCallback() {
                    @Override
                    public void OnCallback(boolean state) {
                        dialog.dismiss();

                        if(state)
                        {
                            showToast("Logged in, successfully!");
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.mainFrameLayout, new MainHome())
                                    .commit();
                        }
                        else
                        {
                            showToast("Failed to login!");
                        }
                    }
                });
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout, new FragmentSignUp())
                        .commit();
            }
        });


        return view;
    }
}