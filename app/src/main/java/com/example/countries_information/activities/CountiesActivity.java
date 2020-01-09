package com.example.countries_information.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries_information.adapters.CountriesAdapter;

public abstract class CountiesActivity extends AppCompatActivity {
    protected RecyclerView countriesRecyclerView;
    protected CountriesAdapter countriesAdapter;

    protected void initRecyclerCountries() {
        countriesRecyclerView.setHasFixedSize(true);
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        countriesRecyclerView.setAdapter(countriesAdapter);
    }
}
