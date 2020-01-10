package com.example.countries_information.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.countries_information.R;
import com.example.countries_information.adapters.CountriesAdapter;
import com.example.countries_information.models.Country;

import java.util.List;

import static com.example.countries_information.CountriesViewModel.BORDERS_EXTRA;
import static com.example.countries_information.CountriesViewModel.COUNTRY_EXTRA;

public class BordersActivity extends CountiesActivity {

    private AppCompatTextView countyTextView;
    private AppCompatTextView borderTextView;
    private AppCompatTextView englishTextView;
    private AppCompatTextView nativeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borders);
        countriesAdapter = new CountriesAdapter(this);
        findViews();
        initRecyclerCountries();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setOriginalCountry(extras);
            setBorders(extras);
        }
    }

    /**
     * set the countries that has border with original country in adapter<br>
     * in case that there is no borders will be a message
     * @param extras that should contains the countries
     */
    private void setBorders(Bundle extras) {
        List<Country> countries = (List<Country>) extras.getSerializable(BORDERS_EXTRA);
        if (countries != null && countries.size() > 0) {
            borderTextView.setText(getString(R.string.borders));
            countriesAdapter.setCountries(countries);
            englishTextView.setVisibility(View.VISIBLE);
            nativeTextView.setVisibility(View.VISIBLE);
        } else {
            borderTextView.setText(getString(R.string.borders_not_found));
            englishTextView.setVisibility(View.GONE);
            nativeTextView.setVisibility(View.GONE);
        }
    }

    /**
     * set the name of original country
     * @param extras that should contains the original country
     */
    private void setOriginalCountry(Bundle extras) {
        Country country = (Country) extras.getSerializable(COUNTRY_EXTRA);
        if (country != null) {
            countyTextView.setText(country.getName());
        }
    }

    private void findViews() {
        countriesRecyclerView = findViewById(R.id.countries_recycler);
        countyTextView = findViewById(R.id.county_original);
        borderTextView = findViewById(R.id.borders_textView);
        englishTextView = findViewById(R.id.english_textView);
        nativeTextView = findViewById(R.id.native_textView);
    }


}
