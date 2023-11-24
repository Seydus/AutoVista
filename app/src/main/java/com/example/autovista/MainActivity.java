package com.example.autovista;

import android.os.Bundle;

import com.example.autovista.authentication.AuthenticationHandler;
import com.example.autovista.authentication.FirebaseAuthentication;
import com.example.autovista.models.User;
import com.example.autovista.ui.UIAuthentication;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuthentication authentication;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GlobalManager globalManager = GlobalManager.getInstance();

        InitializeFirebaseAuthentication();
        InitializeUIAdapter();
    }

    private void InitializeFirebaseAuthentication()
    {
        authentication = new FirebaseAuthentication(this);
        authenticationHandler = new AuthenticationHandler();
    }

    private void InitializeUIAdapter()
    {
        UIAuthentication adapter = new UIAuthentication(this, new User());
        adapter.InitializeViews();
        adapter.InvokeUIAdapter();
    }

    public GoogleSignInOptions getSignInOptions(String clientId) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
    }
}