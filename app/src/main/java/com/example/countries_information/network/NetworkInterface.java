package com.example.countries_information.network;

import com.example.countries_information.models.Country;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NetworkInterface {
    @GET("all/")
    Single<List<Country>> getAllCountries();
}
