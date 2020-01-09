package com.example.countries_information.view_holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.countries_information.R;
import com.example.countries_information.models.Country;

import io.reactivex.subjects.BehaviorSubject;

public class CountyViewHolderClickable extends CountyViewHolder {
    private View root;

    public CountyViewHolderClickable(@NonNull View itemView, BehaviorSubject<Country> clickedSubject) {
        super(itemView);
        root = itemView.findViewById(R.id.country_cardView);
        root.setOnClickListener(view -> clickedSubject.onNext(country));
    }
}
