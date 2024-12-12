package com.example.autovista.car;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.remotedatabase.FirestoreDataCallback;
import com.example.autovista.ui.fragment.FragmentListOfCars;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class CarHandler extends Fragment {
    QuerySnapshot queryData;
    public List<DocumentSnapshot> modelItems;
    public List<DocumentSnapshot> brandItems;
    public DocumentSnapshot model;

    /*
    public List<DocumentSnapshot> RetrieveCarBrandData() {
        GlobalManager.Instance.getFirestoreHelper().OnReadAllCarBrandFirestore(new FirestoreDataCallback() {
            @Override
            public void onCallback(Object data) {
                brandItems = (List<DocumentSnapshot>) data;

                for (DocumentSnapshot document : brandItems) {
                    Log.e("TASDASDASDAD", "Document ID: " + document.getId());
                }
            }
        });

        return brandItems;
    }

     */
}
