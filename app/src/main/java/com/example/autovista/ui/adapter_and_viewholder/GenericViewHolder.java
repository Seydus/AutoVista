package com.example.autovista.ui.adapter_and_viewholder;

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
                ((Button) view).setText((String) data.get("text"));
                view.setOnClickListener((View.OnClickListener) data.get("clickListener"));
            } else if (view instanceof TextView) {
                // Handle other view types if needed
                ((TextView) view).setText((String) data.get("text"));
            }
            else if (view instanceof FrameLayout)
            {
                view.setOnClickListener((View.OnClickListener) data.get("clickListener"));
            }
            // Add more cases for different view types as needed
        }
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int viewResourceId) {
        return (V) itemView.findViewById(viewResourceId);
    }
}

