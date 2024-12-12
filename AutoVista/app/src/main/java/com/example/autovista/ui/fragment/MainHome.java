package com.example.autovista.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainHome extends Fragment {

    BottomNavigationView bottomNavigationView;
    TextView titleTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home_main, container, false);

        InitializeInitialContent();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeBottomNavigation();
    }

    private void InitializeInitialContent()
    {
        int entryCount = requireActivity().getSupportFragmentManager().getBackStackEntryCount();
        Log.d("BackStack", "Back stack entry count: " + entryCount);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new FragmentHome())
                .addToBackStack("home")
                .commit();
    }

    private void InitializeBottomNavigation() {
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.bottom_home) {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHome()).commit();
                    GlobalManager.Instance.setSelected(false);
                    GlobalManager.Instance.setSelectedInformation(false);
                    return true;
                } else if (itemId == R.id.bottom_more) {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMore()).commit();
                    GlobalManager.Instance.setSelected(false);
                    GlobalManager.Instance.setSelectedInformation(false);
                    return true;
                }
                else if (itemId == R.id.bottom_interested) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:09154289822"));
                    startActivity(intent);
                    GlobalManager.Instance.setSelected(false);
                    GlobalManager.Instance.setSelectedInformation(false);
                    return true;
                }

                return true;
            }
        });
    }
}
