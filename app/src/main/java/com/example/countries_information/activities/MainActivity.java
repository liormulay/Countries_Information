package com.example.countries_information.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.countries_information.CountriesViewModel;
import com.example.countries_information.R;
import com.example.countries_information.adapters.CountriesAdapter;
import com.example.countries_information.adapters.CountriesAdapterClickable;
import com.example.countries_information.models.Country;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity {
    private CountriesViewModel countriesViewModel;
    private RecyclerView countriesRecyclerView;
    private CountriesAdapter countriesAdapter;
    private CompositeDisposable disposeOnDestroy = new CompositeDisposable();
    private AppCompatButton sortByAreaButton;
    private AppCompatButton sortByAreaDescendButton;
    private AppCompatButton sortByNameButton;
    private AppCompatButton sortByNameDescendButton;
    private BehaviorSubject<Country> countryChooseSubject = BehaviorSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countriesAdapter = new CountriesAdapterClickable(this, countryChooseSubject);
        countriesViewModel = new CountriesViewModel(this,countryChooseSubject.hide());
        findViews();
        initRecyclerCountries();
        getCountries();
        initActions();
    }

    private void initActions() {
        sortByNameButton.setOnClickListener(view -> countriesViewModel.notifySortByName(false));
        sortByNameDescendButton.setOnClickListener(view -> countriesViewModel.notifySortByName(true));
        sortByAreaButton.setOnClickListener(view -> countriesViewModel.notifySortByArea(false));
        sortByAreaDescendButton.setOnClickListener(view -> countriesViewModel.notifySortByArea(true));
    }

    private void initRecyclerCountries() {
        countriesRecyclerView.setHasFixedSize(true);
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        countriesRecyclerView.setAdapter(countriesAdapter);
    }

    private void getCountries() {
        disposeOnDestroy.add(countriesViewModel.getAllCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countries -> countriesAdapter.setCountries(countries)));
    }

    private void findViews() {
        countriesRecyclerView = findViewById(R.id.countries_recycler);
        sortByNameButton = findViewById(R.id.sort_by_name_button);
        sortByNameDescendButton = findViewById(R.id.sort_by_name_descend_button);
        sortByAreaButton = findViewById(R.id.sort_by_area_button);
        sortByAreaDescendButton = findViewById(R.id.sort_by_area_descend_button);
    }

}
