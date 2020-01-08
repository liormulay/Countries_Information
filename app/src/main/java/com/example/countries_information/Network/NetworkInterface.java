package com.example.countries_information.Network;

import com.example.countries_information.Models.Country;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NetworkInterface {
    @GET("all/")
    Observable<List<Country>> getAllCountries();
}
