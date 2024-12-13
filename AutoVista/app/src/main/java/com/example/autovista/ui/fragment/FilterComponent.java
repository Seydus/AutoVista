package com.example.autovista.ui.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.autovista.R;

import java.util.List;

public class FilterComponent extends LinearLayout {

    private Spinner spinnerModel, spinnerBrand;
    private EditText etMaxPrice;
    private Button btnSearch;

    public FilterComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterComponent(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.filter_component, this, true);

        spinnerModel = findViewById(R.id.spinner_model);
        spinnerBrand = findViewById(R.id.spinner_brand);
        etMaxPrice = findViewById(R.id.et_max_price);
        btnSearch = findViewById(R.id.btn_search);
    }
    public void setModelOptions(List<String> models) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, models);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModel.setAdapter(adapter);
    }
    public void setBrandOptions(List<String> brands) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, brands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(adapter);
    }

    public Button getSearchButton() {
        return btnSearch;
    }

    public String getSelectedModel() {
        return spinnerModel.getSelectedItem() != null ? spinnerModel.getSelectedItem().toString() : "";
    }

    public String getSelectedBrand() {
        return spinnerBrand.getSelectedItem() != null ? spinnerBrand.getSelectedItem().toString() : "";
    }

    public String getMaxPrice() {
        return etMaxPrice.getText().toString();
    }

    public void resetFilters() {
        spinnerModel.setSelection(0);
        spinnerBrand.setSelection(0);
        etMaxPrice.setText("");
    }
}