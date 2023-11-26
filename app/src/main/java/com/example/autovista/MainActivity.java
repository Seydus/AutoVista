package com.example.autovista;

import android.os.Bundle;

import com.example.autovista.authentication.AuthenticationHandler;
import com.example.autovista.authentication.FirebaseAuthentication;
import com.example.autovista.models.car.Car;
import com.example.autovista.models.user.User;
import com.example.autovista.ui.UIAuthentication;
import com.example.ventus.CarAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuthentication authentication;
    private AuthenticationHandler authenticationHandler;

    private RecyclerView RecyclerMain;
    private RecyclerView RecyclerDescription;
    private com.example.ventus.CarAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_item);

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

    private void ShowCarList(List<Car> carList)
    {
        RecyclerMain = findViewById(R.id.recycler_view);
        RecyclerMain.setHasFixedSize(true);
        RecyclerMain.setLayoutManager(new GridLayoutManager(this,1));
        Adapter = new CarAdapter(carList, this);
        RecyclerMain.setAdapter(Adapter);
    }
}