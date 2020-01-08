package com.example.countries_information.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries_information.R;

public class CountyViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView nativeNameTextView;
    private AppCompatTextView englishNameTextView;
    public CountyViewHolder(@NonNull View itemView) {
        super(itemView);
        nativeNameTextView = itemView.findViewById(R.id.native_name_textView);
        englishNameTextView = itemView.findViewById(R.id.english_name_textView);
    }

    public void setNativeNameTextView(String nativeName) {
        nativeNameTextView.setText(nativeName);
    }

    public void setEnglishName(String englishName) {
        englishNameTextView.setText(englishName);
    }
}
