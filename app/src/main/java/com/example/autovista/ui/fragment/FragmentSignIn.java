package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autovista.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentSignIn extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBarBackButton();
    }

    void ActionBarBackButton()
    {
        ImageButton actionBarBackBtn = requireActivity().findViewById(R.id.backBtn);
        actionBarBackBtn.setVisibility(View.GONE);

        TextView titleTxt = requireActivity().findViewById(R.id.titleTxt);
        titleTxt.setText("Profile");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }
}