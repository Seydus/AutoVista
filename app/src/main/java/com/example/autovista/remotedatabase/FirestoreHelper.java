package com.example.autovista.remotedatabase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.autovista.GlobalManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirestoreHelper  {
    private FirebaseFirestore db;

    public FirestoreHelper()
    {
        db = FirebaseFirestore.getInstance();

        GlobalManager.Instance.setFirestoreHelper(this);
    }


    public void OnReadCarInfoFirestore(String brandId, String modelId, FirestoreDataCallback onCallBack) {
        CollectionReference colRef = db.collection("cars").document(brandId).collection("models");
        DocumentReference docRef = colRef.document(modelId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (documentSnapshot.exists()) {
                        onCallBack.onCallback(documentSnapshot);
                        // Log.d("CARINFO", documentSnapshot.getId());
                        onCallBack.onState(true);
                    } else {
                        Log.e("HEARAAR", "Document does not exist.");
                        onCallBack.onState(false);
                    }
                } else {
                    Log.e(TAG, "Error getting document: ", task.getException());
                    onCallBack.onState(false);
                }
            }
        });
    }


    public List<DocumentSnapshot> OnReadAllCarBrandFirestore(FirestoreDataCallback onCallBack) {
        CollectionReference colRef = db.collection("cars");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                        onCallBack.onCallback(documents);

                        for (DocumentSnapshot document : documents) {
                            Log.e("TASDASDASDAD", "Document ID: " + document.getId());
                        }
                        onCallBack.onState(true);
                    } else {
                        Log.e("TASDASDASDAD", "QuerySnapshot is null");
                        onCallBack.onState(false);
                    }
                } else {
                    Log.e("TASDASDASDAD", "Error getting documents: ", task.getException());
                    onCallBack.onState(false);
                }
            }
        });
        return null;
    }

    public DocumentSnapshot OnReadAllCarModelFirestore(String documentId, FirestoreDataCallback onCallBack) {
        CollectionReference colRef = db.collection("cars").document(documentId).collection("models");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                        onCallBack.onCallback(documents);
                        onCallBack.onState(true);

                    } else {
                        Log.e("TASDASDASDAD", "QuerySnapshot is null");
                        onCallBack.onState(false);
                    }
                } else {
                    Log.e("TASDASDASDAD", "Error getting documents: ", task.getException());
                    onCallBack.onState(false);
                }
            }
        });
        return null;
    }
}
