package com.example.autovista;

import android.os.Bundle;
import android.widget.Toast;

import com.example.autovista.firestore.FirestoreDataHandler;
import com.example.autovista.firestore.FirestoreHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.autovista.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, Object> userData = new HashMap<>();
        userData.put("CarName", "Nissan");
        userData.put("VehicleName", "Musta");

        FirestoreHelper helper = new FirestoreHelper();
        FirestoreDataHandler data = new FirestoreDataHandler("users", "micheal", userData, getApplicationContext());

        helper.OnCreateFirestore(data);
    }
}