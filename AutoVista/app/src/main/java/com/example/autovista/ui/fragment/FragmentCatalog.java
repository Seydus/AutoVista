package com.example.autovista.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.autovista.R;
import com.example.autovista.models.CatalogItem;
import com.example.autovista.ui.adapter_and_viewholder.CatalogAdapter;
import com.example.autovista.ui.adapter_and_viewholder.RecommendedAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentCatalog extends Fragment {

    private ViewPager viewPager; // Existing ViewPager
    private CatalogAdapter catalogAdapter; // Existing Adapter

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        viewPager = view.findViewById(R.id.view_pager_catalog);


        List<CatalogItem> catalogItems = new ArrayList<>();
        catalogItems.add(new CatalogItem(R.drawable.audi_car_model, "Model S", "Tesla"));
        catalogItems.add(new CatalogItem(R.drawable.audi_car_model, "Civic", "Honda"));
        catalogItems.add(new CatalogItem(R.drawable.audi_car_model, "Mustang", "Ford"));
        catalogItems.add(new CatalogItem(R.drawable.audi_car_model, "Camry", "Toyota"));
        catalogItems.add(new CatalogItem(R.drawable.audi_car_model, "Accord", "Honda"));
        catalogItems.add(new CatalogItem(R.drawable.audi_car_model, "Wrangler", "Jeep"));

        catalogAdapter = new CatalogAdapter(requireContext(), catalogItems);
        viewPager.setAdapter(catalogAdapter);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_recommended);
        RecommendedAdapter recommendedAdapter = new RecommendedAdapter(requireContext(), catalogItems);
        recyclerView.setAdapter(recommendedAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        return view;
    }
}
