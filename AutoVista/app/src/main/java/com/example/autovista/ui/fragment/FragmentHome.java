package com.example.autovista.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.remotedatabase.FirestoreDataCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class FragmentHome extends Fragment {
    Button btnMakeModel, btnVehicleCategory;

    private void showToast(String message) {
        requireContext();
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

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
        titleTxt.setText("AutoVista");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //btnMakeModel = view.findViewById(R.id.btn_make_model);
        //btnVehicleCategory = view.findViewById(R.id.btn_vehicle_category);

        btnMakeModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(requireContext());
                dialog.setCancelable(false);
                dialog.setTitle("Fetching data...");
                dialog.show();

                GlobalManager.Instance.getFirestoreHelper().OnReadAllCarBrandFirestore(new FirestoreDataCallback() {
                    List<DocumentSnapshot> brandItems;

                    @Override
                    public void onCallback(Object data) {
                        brandItems = (List<DocumentSnapshot>) data;
                    }

                    @Override
                    public void onState(boolean state) {
                        dialog.dismiss();

                        if(state)
                        {
                            int entryCount = requireActivity().getSupportFragmentManager().getBackStackEntryCount();
                            Log.d("BackStack", "Back stack entry count: " + entryCount);


                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayout, new FragmentVehicleModel(brandItems))
                                    .addToBackStack("list")
                                    .commit();
                        }
                        else
                        {
                            showToast("Failed to fetch data");
                        }
                    }
                });
            }
        });

        btnVehicleCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use getActivity() to get the hosting activity
                if (getActivity() != null) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, new FragmentVehicleCategory())
                            .commit();
                }
            }
        });
        return view;
    }
}