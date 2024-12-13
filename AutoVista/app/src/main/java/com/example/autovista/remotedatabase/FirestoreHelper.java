package com.example.autovista.remotedatabase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FirestoreHelper {

    private FirebaseFirestore firestore;

    public FirestoreHelper() {
        this.firestore = FirebaseFirestore.getInstance();
        Log.d("FirestoreHelper", "Firestore instance initialized");
    }

    // Fetch all car brands
    public void fetchCarBrands(FirestoreCallback callback) {
        firestore.collection("cars")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> carBrands = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            carBrands.add(document.getId());
                            Log.d("FirestoreHelper", "Brand: " + document.getId());
                        }
                        callback.onSuccess(carBrands);
                    } else {
                        Log.e("FirestoreHelper", "Error fetching car brands", task.getException());
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Fetch random brands and their random models
    public void fetchRandomBrandsAndModels(FirestoreCallback callback) {
        fetchCarBrands(new FirestoreCallback() {
            @Override
            public void onSuccess(Object data) {
                List<String> allBrands = (List<String>) data;

                // Ensure there are enough brands
                if (allBrands.size() < 4) {
                    Log.e("FirestoreHelper", "Not enough brands in the database");
                    callback.onFailure(new Exception("Not enough brands in the database"));
                    return;
                }

                // Randomly shuffle and select 4 brands
                Collections.shuffle(allBrands);
                List<String> selectedBrands = allBrands.subList(0, 4);

                Map<String, Map<String, Object>> result = new HashMap<>();

                for (String brand : selectedBrands) {
                    firestore.collection("cars")
                            .document(brand)
                            .collection("models")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    List<DocumentSnapshot> allModels = task.getResult().getDocuments();
                                    if (!allModels.isEmpty()) {
                                        // Pick a random model for the brand
                                        DocumentSnapshot randomModel = allModels.get(new Random().nextInt(allModels.size()));
                                        Map<String, Object> modelDetails = randomModel.getData();

                                        // Use document ID as modelName if the field is missing
                                        String modelName = randomModel.getId();

                                        // Add the modelName to the modelDetails map if it's not present
                                        if (modelDetails != null) {
                                            modelDetails.put("modelName", modelName);
                                        } else {
                                            // Create a fallback modelDetails map if it's null
                                            modelDetails = new HashMap<>();
                                            modelDetails.put("modelName", modelName);
                                        }

                                        Log.d("Firestore Debug", "Brand: " + brand + ", Model Name: " + modelName);
                                        result.put(brand, modelDetails);

                                        // Check if all 4 brands have been processed
                                        if (result.size() == 4) {
                                            callback.onSuccess(result);
                                        }
                                    } else {
                                        Log.e("FirestoreHelper", "No models found for brand: " + brand);
                                    }
                                } else {
                                    Log.e("FirestoreHelper", "Error fetching models for brand: " + brand, task.getException());
                                    callback.onFailure(task.getException());
                                }
                            });

                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("FirestoreHelper", "Failed to fetch car brands", e);
                callback.onFailure(e);
            }
        });
    }

    // Fetch models for a specific brand
    public void fetchCarModels(String brand, FirestoreCallback callback) {
        Log.d("FirestoreHelper", "Fetching models for brand: " + brand);
        firestore.collection("cars")
                .document(brand)
                .collection("models")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Map<String, Object>> carModels = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Map<String, Object> modelDetails = document.getData();
                            if (modelDetails != null) {
                                modelDetails.put("documentId", document.getId()); // Add document ID as model name fallback
                            }
                            Log.d("FirestoreHelper", "Model: " + document.getId() + ", Data: " + modelDetails);
                            carModels.add(modelDetails);
                        }
                        callback.onSuccess(carModels);
                    } else {
                        Log.e("FirestoreHelper", "Error fetching car models for brand: " + brand, task.getException());
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Fetch details of a specific car model
    public void fetchModelDetails(String brand, String model, FirestoreCallback callback) {
        Log.d(TAG, "Fetching details for model: " + model + " of brand: " + brand);
        firestore.collection("cars")
                .document(brand)
                .collection("models")
                .document(model)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "Fetched model details for: " + model);
                            callback.onSuccess(document.getData());
                        } else {
                            Log.e(TAG, "No such model document found: " + model);
                            callback.onFailure(new Exception("Document not found"));
                        }
                    } else {
                        Log.e(TAG, "Error fetching model details for: " + model, task.getException());
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Callback interface for handling Firestore responses
    public interface FirestoreCallback {
        void onSuccess(Object data);

        void onFailure(Exception e);
    }
}
