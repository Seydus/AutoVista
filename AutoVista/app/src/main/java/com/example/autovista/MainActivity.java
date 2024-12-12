package com.example.autovista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovista.authentication.FirebaseAuthentication;
import com.example.autovista.car.CarHandler;
import com.example.autovista.remotedatabase.FirestoreDataCallback;
import com.example.autovista.remotedatabase.FirestoreHelper;
import com.example.autovista.ui.fragment.FragmentCarInformation;
import com.example.autovista.ui.fragment.FragmentListOfCars;
import com.example.autovista.ui.fragment.FragmentLogin;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuthentication authentication;
    private RecyclerView RecyclerMain;
    private RecyclerView RecyclerDescription;

    private TabLayout tabLayout;
    FrameLayout frameLayout;
    public ProgressDialog dialog;
    String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout, new FragmentLogin())
                .commit();

        InitializeSingleton();
        InitializeFirebaseAuthentication();
    }

    private void InitializeFirebaseAuthentication()
    {
        authentication = new FirebaseAuthentication(this);
        GlobalManager.Instance.setFirebaseAuthentication(authentication);
    }

    private void InitializeSingleton()
    {
        GlobalManager globalManager = GlobalManager.getInstance();
        FirestoreHelper firestoreHelper = new FirestoreHelper();
        globalManager.setFirestoreHelper(firestoreHelper);
    }
}