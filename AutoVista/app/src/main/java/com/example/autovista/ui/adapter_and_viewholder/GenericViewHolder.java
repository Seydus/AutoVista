package com.example.autovista.ui.adapter_and_viewholder;

import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovista.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenericViewHolder<T> extends RecyclerView.ViewHolder {

    public GenericViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Map<String, Object> data, int... viewIds) {
        for (int viewId : viewIds) {
            View view = itemView.findViewById(viewId);

            if (view instanceof Button) {
                ((Button) view).setText((String) data.get(String.valueOf(viewId)));
                view.setOnClickListener((View.OnClickListener) data.get("clickListener"));
            } else if (view instanceof TextView) {
                ((TextView) view).setText((String) data.get(String.valueOf(viewId)));
            } else if (view instanceof ImageView) {
                // Handle ImageView separately
                setImageForImageView((ImageView) view, (String) data.get(String.valueOf(viewId)));
            } else if (view instanceof FrameLayout) {
                view.setOnClickListener((View.OnClickListener) data.get("clickListener"));
            }

            // Add more cases for different view types as needed
        }
    }

    private void setImageForImageView(ImageView imageView, String completePath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Reference to the image file
        StorageReference imageRef = storageRef.child(completePath);

        Log.e("PATHFORCARs", completePath);

        try {
            File localFile = File.createTempFile("images", "png");
            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Local file has been created
                            // Set the local file as the image resource
                            imageView.setImageURI(Uri.fromFile(localFile));
                            Log.e("IMAGELOADER", "IMAGE SUCCESSFULLY LOADED");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("IMAGELOADER", "FAILED TO LOAD IMAGE: " + exception.getMessage());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IMAGELOADER", "EXCEPTION: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int viewResourceId) {
        return (V) itemView.findViewById(viewResourceId);
    }
}

