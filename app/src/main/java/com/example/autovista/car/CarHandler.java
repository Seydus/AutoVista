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
    public DocumentSnapshot item;

    public void RetrieveCarBrandData() {
        GlobalManager.Instance.getFirestoreHelper().OnReadAllCarBrandFirestore(new FirestoreDataCallback() {
            @Override
            public void onCallback(Object data) {
                brandItems = (List<DocumentSnapshot>) data;

                for (DocumentSnapshot document : brandItems) {
                    Log.e("TASDASDASDAD", "Document ID: " + document.getId());
                }
            }
        });
    }

    public DocumentSnapshot RetriveCarInfo(String documentId)
    {
        GlobalManager.Instance.getFirestoreHelper().OnReadFirestore(documentId, new FirestoreDataCallback() {
            @Override
            public void onCallback(Object data) {
                item = (DocumentSnapshot) data;

                GlobalManager.Instance.getMainActivity().GoNextModelInformationPage(item.getId());
            }
        });

        return item;
    }

    public List<DocumentSnapshot> RetrieveCarBrandModelData(String documentId)
    {
        GlobalManager.Instance.getFirestoreHelper().OnReadAllCarModelFirestore(documentId, new FirestoreDataCallback() {
            @Override
            public void onCallback(Object data) {
                modelItems = (List<DocumentSnapshot>) data;

                for (DocumentSnapshot document : modelItems) {
                    Log.e("TASDASDASDAD", "Document ID: " + document.getId());
                }

                GlobalManager.Instance.getMainActivity().GoNextModelPage(documentId);
            }
        });

        return modelItems;
    }
}
