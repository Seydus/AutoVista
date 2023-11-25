package com.example.autovista.car;

import android.util.Log;

import com.example.autovista.GlobalManager;
import com.example.autovista.remotedatabase.FirestoreDataCallback;
import com.example.autovista.remotedatabase.FirestoreDataHandler;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CarHandler {
    QuerySnapshot queryData;

    void RetrieveCarData() {
        GlobalManager.Instance.getFirestoreHelper().OnReadAllFirestore("cars", new FirestoreDataCallback() {
            @Override
            public void onCallback(QuerySnapshot data) {
                queryData = data;
            }
        });
    }

    public void getDataAsString() {
        queryData.forEach(documentSnapshot -> {
            String data = documentSnapshot.getData().toString();
            Log.d("Data", data);
        });
    }
}
