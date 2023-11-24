package com.example.autovista.firestore;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirestoreHelper {
    private FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public void OnCreateFirestore(FirestoreDataHandler dataHandler) {
        db.collection(dataHandler.collectionName)
                .document(dataHandler.documentId)
                .set(dataHandler.documentData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(dataHandler.appContext, "Success", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(dataHandler.appContext, "Failure", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void OnReadFirestore(FirestoreDataHandler dataHandler)
    {
        db = FirebaseFirestore.getInstance();

        db.collection(dataHandler.collectionName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void OnUpdateFirestore(FirestoreDataHandler dataHandler)
    {
        DocumentReference defRec = db.collection(dataHandler.collectionName).document(dataHandler.documentId);

        defRec.set(dataHandler.documentData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(dataHandler.appContext, "Success", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(dataHandler.appContext, "Failure", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void OnDeleteFirestore(FirestoreDataHandler dataHandler) {
        DocumentReference documentReference = db.collection(dataHandler.collectionName).document(dataHandler.documentId);

        documentReference
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(dataHandler.appContext, "Document successfully deleted", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(dataHandler.appContext, "Error deleting document", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
