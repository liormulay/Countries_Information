package com.example.countries_information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.countries_information.adapters.CountriesAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    private CountriesViewModel countriesViewModel;
    private RecyclerView countriesRecyclerView;
    private CountriesAdapter countriesAdapter;
    private CompositeDisposable disposeOnDestroy = new CompositeDisposable();
    private AppCompatButton sortByAreaButton;
    private AppCompatButton sortByAreaDescendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countriesAdapter = new CountriesAdapter(this);
        countriesViewModel = new CountriesViewModel();
        findViews();
        initActions();
        initRecyclerCountries();
        getAllCountries();
    }

    private void initActions() {
        sortByAreaButton.setOnClickListener(view -> countriesViewModel.notifySortByArea(false));
        sortByAreaDescendButton.setOnClickListener(view -> countriesViewModel.notifySortByArea(true));
    }

    private void initRecyclerCountries() {
        countriesRecyclerView.setHasFixedSize(true);
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        countriesRecyclerView.setAdapter(countriesAdapter);
    }

    private void getAllCountries() {
        disposeOnDestroy.add(countriesViewModel.getAllCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countries -> countriesAdapter.setCountries(countries)));
    }

    private void findViews() {
        countriesRecyclerView = findViewById(R.id.countries_recycler);
        sortByAreaButton = findViewById(R.id.sort_by_area_button);
        sortByAreaDescendButton = findViewById(R.id.sort_by_area_descend_button);
    }

}
