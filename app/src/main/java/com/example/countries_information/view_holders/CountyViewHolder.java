package com.example.countries_information.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries_information.R;
import com.example.countries_information.models.Country;

public class CountyViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView nativeNameTextView;
    private AppCompatTextView englishNameTextView;
    protected Country country;

    public CountyViewHolder(@NonNull View itemView) {
        super(itemView);
        nativeNameTextView = itemView.findViewById(R.id.native_name_textView);
        englishNameTextView = itemView.findViewById(R.id.english_name_textView);
    }

    public void bindData(Country country) {
        this.country = country;
        nativeNameTextView.setText(country.getNativeName());
        englishNameTextView.setText(country.getName());
    }
}
