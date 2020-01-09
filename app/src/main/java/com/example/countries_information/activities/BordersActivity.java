package com.example.countries_information.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries_information.R;
import com.example.countries_information.adapters.CountriesAdapter;
import com.example.countries_information.adapters.CountriesAdapterClickable;

public class BordersActivity extends AppCompatActivity {

    private RecyclerView countriesRecyclerView;
    private CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borders);
        countriesAdapter = new CountriesAdapter(this);
        findViews();

    }

    private void findViews() {
        countriesRecyclerView = findViewById(R.id.countries_recycler);
    }


}
