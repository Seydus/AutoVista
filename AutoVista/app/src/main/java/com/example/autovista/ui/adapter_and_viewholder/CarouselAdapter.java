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
import com.example.autovista.models.CarouselItem;

import java.util.List;

public class CarouselAdapter extends PagerAdapter {

    private Context context;
    private List<CarouselItem> items;

    public CarouselAdapter(Context context, List<CarouselItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carousel, container, false);

        ImageView carImage = view.findViewById(R.id.car_image);
        TextView carName = view.findViewById(R.id.car_name);

        CarouselItem item = items.get(position);
        carImage.setImageResource(item.getImageResId());
        carName.setText(item.getCarName());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
