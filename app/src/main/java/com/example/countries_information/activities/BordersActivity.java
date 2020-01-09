package com.example.countries_information.activities;

import android.os.Bundle;

import com.example.countries_information.R;
import com.example.countries_information.adapters.CountriesAdapter;

public class BordersActivity extends CountriesActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borders);

    }



    @Override
    protected CountriesAdapter getCountriesAdapter() {
        return null;
    }


}
