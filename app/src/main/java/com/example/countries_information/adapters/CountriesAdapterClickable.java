package com.example.countries_information.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.countries_information.R;
import com.example.countries_information.models.Country;
import com.example.countries_information.view_holders.CountyViewHolder;
import com.example.countries_information.view_holders.CountyViewHolderClickable;

import io.reactivex.subjects.BehaviorSubject;

public class CountriesAdapterClickable extends CountriesAdapter {
    private BehaviorSubject<Country> clickedSubject;

    public CountriesAdapterClickable(Context context, BehaviorSubject<Country> clickedSubject) {
        super(context);
        this.clickedSubject = clickedSubject;
    }

    @NonNull
    @Override
    public CountyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountyViewHolderClickable(LayoutInflater.from(context)
                .inflate(R.layout.row_country, parent, false), clickedSubject);
    }
}
