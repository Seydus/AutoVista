package com.example.autovista;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autovista.authentication.AuthenticationHandler;
import com.example.autovista.authentication.FirebaseAuthentication;
import com.example.autovista.remotedatabase.FirestoreHelper;
import com.example.autovista.ui.UIAuthentication;

public class GlobalManager extends AppCompatActivity {
    public static GlobalManager Instance;
    private FirebaseAuthentication firebaseAuthentication;
    private FirestoreHelper firestoreHelper;
    private UIAuthentication uiAdapter;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static synchronized GlobalManager getInstance() {
        if (Instance == null) {
            Instance = new GlobalManager();
        }

        return Instance;
    }

    public FirebaseAuthentication getFirebaseAuthentication() {
        return firebaseAuthentication;
    }

    public void setFirebaseAuthentication(FirebaseAuthentication firebaseAuthentication) {
        this.firebaseAuthentication = firebaseAuthentication;
    }

    public FirestoreHelper getFirestoreHelper() {
        return firestoreHelper;
    }

    public void setFirestoreHelper(FirestoreHelper firestoreHelper) {
        this.firestoreHelper = firestoreHelper;
    }

    public UIAuthentication getUiAdapter() {
        return uiAdapter;
    }

    public void setUiAdapter(UIAuthentication uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    public AuthenticationHandler getAuthenticationHandler() {
        return authenticationHandler;
    }

    public void setAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }
}