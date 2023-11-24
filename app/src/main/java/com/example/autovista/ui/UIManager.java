package com.example.autovista.ui;

import android.app.Activity;
import android.app.GameManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;

public class UIManager {
    private Context context;

    public UIManager(Context context) {
        this.context = context;
        GlobalManager.Instance.setUiAdapter(this);
    }

    public void InvokeUIAdapter() {
        SignInButton();
    }

    public void SignInButton() {
        // Make sure the context is not null before attempting to find the button
        if (context != null) {
            Button button = ((Activity) context).findViewById(R.id.supabutton);
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        GlobalManager.Instance.getFirebaseAuthentication().signIn();
                    }
                });
            } else {
                Log.e("UIManager", "Button not found in the layout");
            }
        } else {
            Log.e("UIManager", "Context is null");
        }
    }
}
