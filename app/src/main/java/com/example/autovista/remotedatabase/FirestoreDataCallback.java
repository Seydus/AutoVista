package com.example.autovista.remotedatabase;

import com.example.autovista.remotedatabase.FirestoreDataHandler;
import com.google.firebase.firestore.QuerySnapshot;

import retrofit2.http.Query;

public interface FirestoreDataCallback {
    void onCallback(QuerySnapshot data);
}