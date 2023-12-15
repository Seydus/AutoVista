package com.example.autovista;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autovista.authentication.AuthenticationHandler;
import com.example.autovista.authentication.FirebaseAuthentication;
import com.example.autovista.car.CarHandler;
import com.example.autovista.remotedatabase.FirestoreHelper;
import com.example.autovista.ui.UIAuthentication;

public class GlobalManager extends AppCompatActivity {
    public static GlobalManager Instance;
    private FirebaseAuthentication firebaseAuthentication;
    private FirestoreHelper firestoreHelper;
    private UIAuthentication uiAdapter;
    private AuthenticationHandler authenticationHandler;
    private CarHandler carHandler;
    private MainActivity mainActivity;

    private Boolean isSelected = false;
    private Boolean isSelectedInformation = false;

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

    public static void setInstance(GlobalManager instance) {
        Instance = instance;
    }

    public CarHandler getCarHandler() {
        return carHandler;
    }

    public void setCarHandler(CarHandler carHandler) {
        this.carHandler = carHandler;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Boolean getSelectedInformation() {
        return isSelectedInformation;
    }

    public void setSelectedInformation(Boolean selectedInformation) {
        isSelectedInformation = selectedInformation;
    }
}