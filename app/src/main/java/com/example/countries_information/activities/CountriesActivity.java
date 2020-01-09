package com.example.countries_information.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries_information.R;
import com.example.countries_information.adapters.CountriesAdapter;

public abstract class CountriesActivity extends AppCompatActivity {
    protected CountriesAdapter countriesAdapter;
    protected RecyclerView countriesRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries_layout);
        countriesAdapter = getCountriesAdapter();
        initRecyclerCountries();
    }


    protected abstract CountriesAdapter getCountriesAdapter();



    private void initRecyclerCountries() {
        countriesRecyclerView = findViewById(R.id.countries_recycler);
        countriesRecyclerView.setHasFixedSize(true);
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        countriesRecyclerView.setAdapter(countriesAdapter);
    }
}
