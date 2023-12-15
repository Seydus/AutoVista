package com.example.autovista.remotedatabase;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.autovista.GlobalManager;
import com.example.autovista.R;
import com.example.autovista.ui.fragment.FragmentListOfCars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirestoreHelper  {
    private FirebaseFirestore db;

    public FirestoreHelper()
    {
        db = FirebaseFirestore.getInstance();

        GlobalManager.Instance.setFirestoreHelper(this);
    }


    public void OnReadFirestore(String documentId, FirestoreDataCallback onCallBack) {
        CollectionReference colRef = db.collection("cars").document(documentId).collection("models");
        DocumentReference docRef = colRef.document(documentId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Log.e("HEARAAR", documentSnapshot.getId());

                    if (documentSnapshot.contains("VIN")) {
                        String vinValue = documentSnapshot.getString("VIN");
                        Log.e("HEARAAR", "VIN: " + vinValue);
                    } else {
                        Log.e("HEARAAR", "VIN field does not exist in the document.");
                    }

                    onCallBack.onCallback(documentSnapshot);
                } else {
                    Log.d(TAG, "Error getting document: ", task.getException());
                }
            }
        });
    }

    public void OnReadAllCarBrandFirestore(FirestoreDataCallback onCallBack) {
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
                    } else {
                        Log.e("TASDASDASDAD", "QuerySnapshot is null");
                    }
                } else {
                    Log.e("TASDASDASDAD", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void OnReadAllCarModelFirestore(String documentId, FirestoreDataCallback onCallBack) {
        CollectionReference colRef = db.collection("cars").document(documentId).collection("models");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                        onCallBack.onCallback(documents);
                    } else {
                        Log.e("TASDASDASDAD", "QuerySnapshot is null");
                    }
                } else {
                    Log.e("TASDASDASDAD", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
