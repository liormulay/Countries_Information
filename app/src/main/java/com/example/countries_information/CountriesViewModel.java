package com.example.countries_information;

import com.example.countries_information.models.Country;
import com.example.countries_information.network.NetworkClient;
import com.example.countries_information.network.NetworkInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel {
    public Observable<List<Country>> getAllCountries() {
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getAllCountries()
                .subscribeOn(Schedulers.io());
    }
}
