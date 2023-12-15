package com.example.autovista;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovista.authentication.AuthenticationHandler;
import com.example.autovista.authentication.FirebaseAuthentication;
import com.example.autovista.car.CarHandler;
import com.example.autovista.models.user.User;
import com.example.autovista.remotedatabase.FirestoreHelper;
import com.example.autovista.ui.UIAuthentication;
import com.example.autovista.ui.fragment.FragmentCarInformation;
import com.example.autovista.ui.fragment.FragmentHome;
import com.example.autovista.ui.fragment.FragmentListOfCars;
import com.example.autovista.ui.fragment.FragmentMore;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuthentication authentication;
    private AuthenticationHandler authenticationHandler;

    private RecyclerView RecyclerMain;
    private RecyclerView RecyclerDescription;

    private TabLayout tabLayout;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InitializeSingleton();
        InitializeInitialContent();
        InitializeBottomNavigation();
        InitializeFirebaseAuthentication();
        InitializeUIAdapter();

    }

    private void InitializeSingleton()
    {
        GlobalManager globalManager = GlobalManager.getInstance();
        FirestoreHelper firestoreHelper = new FirestoreHelper();
        globalManager.setFirestoreHelper(firestoreHelper);
        CarHandler carHandler = new CarHandler();
        carHandler.RetrieveCarBrandData();
        globalManager.setCarHandler(carHandler);
        globalManager.setMainActivity(this);
    }

    private void InitializeInitialContent()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new FragmentHome())
                .commit();
    }

    private void InitializeBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.bottom_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHome()).commit();
                    GlobalManager.Instance.setSelected(false);
                    GlobalManager.Instance.setSelectedInformation(false);
                    return true;
                } else if (itemId == R.id.bottom_more) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMore()).commit();
                    GlobalManager.Instance.setSelected(false);
                    GlobalManager.Instance.setSelectedInformation(false);
                    return true;
                }

                return true;
            }
        });
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

    public void GoNextModelPage(String model)
    {
        if(!GlobalManager.Instance.getSelected())
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new FragmentListOfCars(model))
                    .commit();
            GlobalManager.Instance.setSelected(true);
        }
    }

    public void GoNextModelInformationPage(String model)
    {
        if(!GlobalManager.Instance.getSelectedInformation())
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new FragmentCarInformation(model))
                    .commit();
            GlobalManager.Instance.setSelectedInformation(true);
        }
    }
}