package com.example.autovista.firestore;

import android.content.Context;

import java.util.Map;

public class FirestoreDataHandler {
    public String collectionName;
    public String documentId;
    public Map<String, Object> documentData;
    public Context appContext;

    public FirestoreDataHandler (String collectionName, String documentId, Map<String, Object> documentData, Context appContext) {
        this.collectionName = collectionName;
        this.documentId = documentId;
        this.documentData = documentData;
        this.appContext = appContext;
    }
}
