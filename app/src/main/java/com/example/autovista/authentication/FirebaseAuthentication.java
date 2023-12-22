package com.example.autovista.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.models.user.User;
import com.example.autovista.ui.fragment.FragmentSignUp;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseAuthentication extends AppCompatActivity {
    private static final String TAG = "FirebaseAuthentication";
    private FirebaseAuth mAuth;
    private Context mContext; // Add Context variable

    public FirebaseAuthentication(Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        this.mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // reload();
        }
    }

    public void register(User user, AuthenticationCallback state) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, task -> {
                    Log.d("AUTHENTICATION", user.getEmail() + " and " + user.getPassword());

                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        assert firebaseUser != null;

                        updateProfileAndUI(firebaseUser, user.getFirstName(), user.getLastName());
                        Log.d("AUTHENTICATION", "User registered.");
                        state.OnCallback(true);
                    } else {
                        Log.w("AUTHENTICATION", "createUserWithEmail:failure", task.getException());
                        state.OnCallback(false);
                    }
                });
    }

    private void updateProfileAndUI(FirebaseUser firebaseUser, String firstName, String lastName) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(firstName + " " + lastName)
                // You can also set other profile information here if needed
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("AUTHENTICATION", "User profile updated.");
                    }
                });
    }

    public void logIn(User user, AuthenticationCallback state) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AUTHENTICATION", user.getEmail() + " and " + user.getPassword());

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTHENTICATION", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            state.OnCallback(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTHENTICATION", "signInWithEmail:failure", task.getException());
                            state.OnCallback(false);
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Email sent
                                //showToast("Verification email sent");
                            } else {
                                // Handle the case where email verification failed
                                //showToast("Failed to send verification email");
                            }
                        }
                    });
        }
    }
}
