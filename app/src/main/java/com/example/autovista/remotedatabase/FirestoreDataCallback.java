package com.example.autovista.remotedatabase;

public interface FirestoreDataCallback {
    void onCallback(Object data);
    void onState(boolean state);
}