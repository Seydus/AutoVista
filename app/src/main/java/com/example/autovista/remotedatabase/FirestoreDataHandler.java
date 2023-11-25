package com.example.autovista.remotedatabase;

import android.content.Context;

import java.util.Map;

public class FirestoreDataHandler {
    public Context appContext;
    public String collectionName;
    public String documentId;
    public Map<String, Object> documentData;

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Map<String, Object> getDocumentData() {
        return documentData;
    }

    public void setDocumentData(Map<String, Object> documentData) {
        this.documentData = documentData;
    }
}