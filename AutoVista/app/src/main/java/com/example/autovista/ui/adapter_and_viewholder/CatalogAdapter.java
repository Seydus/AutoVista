package com.example.autovista.ui.adapter_and_viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.autovista.R;
import com.example.autovista.models.CatalogItem;

import java.util.List;

public class CatalogAdapter extends PagerAdapter {

    private Context context;
    private List<CatalogItem> catalogItems;

    public CatalogAdapter(Context context, List<CatalogItem> catalogItems) {
        this.context = context;
        this.catalogItems = catalogItems;
    }

    @Override
    public int getCount() {
        return catalogItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_catalog, container, false);

        ImageView itemImage = view.findViewById(R.id.item_image);
        TextView itemModelName = view.findViewById(R.id.item_model_name);
        TextView itemBrand = view.findViewById(R.id.item_brand);

        CatalogItem item = catalogItems.get(position);
        itemImage.setImageResource(item.getImageResId());
        itemModelName.setText(item.getModelName());
        itemBrand.setText(item.getBrand());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

