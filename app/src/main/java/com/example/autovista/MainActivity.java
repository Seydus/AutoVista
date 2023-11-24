package com.example.autovista;

import android.os.Bundle;
import android.widget.Button;

import com.example.autovista.authentication.FirebaseAuthentication;
import com.example.autovista.ui.UIManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalManager globalManager = GlobalManager.getInstance();

        InitializeFirebaseAuthentication();
        InitializeUIAdapter();
//
        FirebaseAuthentication testAuth = GlobalManager.Instance.getFirebaseAuthentication();
        testAuth.signIn();
    }

    private void InitializeFirebaseAuthentication()
    {
        String clientId = getString(R.string.default_web_client_id);
        GoogleSignInOptions gso = getSignInOptions(getString(R.string.default_web_client_id));
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseAuthentication authentication = new FirebaseAuthentication(mGoogleSignInClient, clientId);
    }

    private void InitializeUIAdapter()
    {
        UIManager adapter = new UIManager(this);
        adapter.InvokeUIAdapter();
    }

    public GoogleSignInOptions getSignInOptions(String clientId) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
    }
}