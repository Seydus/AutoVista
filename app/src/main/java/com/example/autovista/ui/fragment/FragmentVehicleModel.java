package com.example.autovista.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.models.car.CarItemsProfile;
import com.example.autovista.remotedatabase.FirestoreDataCallback;
import com.example.autovista.ui.adapter_and_viewholder.GenericAdapter;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FragmentVehicleModel extends Fragment {

    List<DocumentSnapshot> brand;

    private void showToast(String message) {
        requireContext();
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    public FragmentVehicleModel(List<DocumentSnapshot> brand)
    {
        this.brand = brand;
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
        titleTxt.setText("Models");

        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBarBackBtn.setVisibility(View.GONE);
                int entryCount = requireActivity().getSupportFragmentManager().getBackStackEntryCount();
                Log.d("BackStack", "Back stack entry count: " + entryCount);

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        GlobalManager.Instance.setSelected(false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);

        List<Map<String, Object>> dataList = new ArrayList<>();

        for (DocumentSnapshot item : brand) {
            String buttonText = item.getId();
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button brand = v.findViewById(R.id.car_item_text_button);
                    ProgressDialog dialog = new ProgressDialog(requireContext());
                    dialog.setCancelable(false);
                    dialog.setTitle("Fetching data...");
                    dialog.show();

                    GlobalManager.Instance.getFirestoreHelper().OnReadAllCarModelFirestore(brand.getText().toString(), new FirestoreDataCallback() {
                        List<DocumentSnapshot> modelItems;
                        CarItemsProfile profile;

                        @Override
                        public void onCallback(Object data) {
                            try {
                                profile = new CarItemsProfile();
                                modelItems = (List<DocumentSnapshot>) data;
                                profile.setBrand(brand.getText().toString());
                                profile.setModelItem(modelItems);
                            } catch (Exception e) {
                                // Handle exceptions, log, or show an error message
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onState(boolean state) {
                            dialog.dismiss();

                            if (state && isAdded()) {
                                int entryCountBeforeTransaction = requireActivity().getSupportFragmentManager().getBackStackEntryCount();
                                Log.d("BackStack", "Back stack entry count before transaction: " + entryCountBeforeTransaction);

                                requireActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, new FragmentListOfCars(profile))
                                        .addToBackStack("modelList")
                                        .commit();

                                int entryCountAfterTransaction = requireActivity().getSupportFragmentManager().getBackStackEntryCount();
                                Log.d("BackStack", "Back stack entry count after transaction: " + entryCountAfterTransaction);
                            } else {
                                showToast("Failed to fetch data");
                            }
                        }
                    });
                }
            };

            Map<String, Object> data = new HashMap<>();
            data.put(String.valueOf(R.id.car_item_text_button), buttonText);
            data.put("clickListener", onClickListener);
            // Add more parameters as needed

            dataList.add(data);
        }

        int layoutResourceId = R.layout.car_image_name_item;

        GenericAdapter adapter = new GenericAdapter(dataList, layoutResourceId, R.id.car_item_text_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }


}