package com.example.countries_information.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries_information.R;
import com.example.countries_information.models.Country;
import com.example.countries_information.view_holders.CountyViewHolder;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountyViewHolder> {
    private Context context;
    private List<Country> countries;

    public CountriesAdapter(Context context) {
        this.context = context;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_country, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountyViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.setNativeNameTextView(country.getNativeName());
        holder.setEnglishName(country.getName());

    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }
}
