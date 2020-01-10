package com.example.countries_information;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import androidx.lifecycle.ViewModel;

import com.example.countries_information.activities.BordersActivity;
import com.example.countries_information.models.Country;
import com.example.countries_information.network.NetworkClient;
import com.example.countries_information.network.NetworkInterface;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CountriesViewModel extends ViewModel {
    private BehaviorSubject<List<Country>> allCountriesBehaviorSubject = BehaviorSubject.create();
    private List<Country> originalCountries = new ArrayList<>();
    private CompositeDisposable onClearedDispose = new CompositeDisposable();
    private Context context;
    public static final String COUNTRY_EXTRA = "country extra";
    public static final String BORDERS_EXTRA = "borders extra";

    public CountriesViewModel(Context context, Observable<Country> countryChoose) {
        this.context = context;
        onClearedDispose.add(countryChoose
                .subscribeOn(Schedulers.io())
                .map(country -> {
                    List<Country> borders = findBorders(country);
                    return Pair.create(country, borders);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::startBordersActivity));

        onClearedDispose.add(getAllCountriesFromApi()
                .subscribe(countries -> {
                    originalCountries = countries;
                    allCountriesBehaviorSubject.onNext(originalCountries);
                }, Throwable::printStackTrace));
    }

    private void startBordersActivity(Pair<Country, List<Country>> countryListPair) {
        Intent intent = new Intent(context, BordersActivity.class);
        intent.putExtra(COUNTRY_EXTRA, countryListPair.first);
        ArrayList<Country> countries = new ArrayList<>(countryListPair.second);
        intent.putExtra(BORDERS_EXTRA, countries);
        context.startActivity(intent);
    }


    private List<Country> findBorders(Country country) {
        List<String> bordersCodes = country.getBorders();
        List<Country> borders = new ArrayList<>();
        for (String borderCode : bordersCodes) {
            for (Country originalCountry : originalCountries) {
                if (originalCountry.getAlpha3Code().equals(borderCode)) {
                    borders.add(originalCountry);
                    break;
                }
            }
        }
        return borders;
    }

    public Single<List<Country>> getAllCountriesFromApi() {
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getAllCountries()
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Country>> getAllCountries() {
        return allCountriesBehaviorSubject.hide();
    }

    private Single<List<Country>> sortByArea(boolean isDescended) {
        return Observable.fromIterable(originalCountries)
                .subscribeOn(Schedulers.io())
                .sorted((country1, country2) -> sortByArea(country1.getArea(), country2.getArea(), isDescended))
                .toList();

    }

    private int sortByArea(String area1String, String area2String, boolean isDescended) {
        double area1 = getDoubleArea(area1String, isDescended);
        double area2 = getDoubleArea(area2String, isDescended);
        return isDescended ? Double.compare(area2, area1) : Double.compare(area1, area2);

    }

    private double getDoubleArea(String areaString, boolean isDescended) {
        return Strings.isNullOrEmpty(areaString) ?
                isDescended ? 0 : Double.POSITIVE_INFINITY
                : Double.valueOf(areaString);
    }

    private Single<List<Country>> sortByName(boolean isDescended) {
        return Observable.fromIterable(originalCountries)
                .subscribeOn(Schedulers.io())
                .sorted((country1, country2) -> isDescended ? country2.getName().compareTo(country1.getName())
                        : country1.getName().compareTo(country2.getName()))
                .toList();

    }

    public void notifySortByArea(boolean isDescended) {
        onClearedDispose.add(sortByArea(isDescended)
                .subscribe(countries -> allCountriesBehaviorSubject.onNext(countries)));
    }

    public void notifySortByName(boolean isDescended) {
        onClearedDispose.add(sortByName(isDescended)
                .subscribe(countries -> allCountriesBehaviorSubject.onNext(countries)));
    }

    @Override
    protected void onCleared() {
        onClearedDispose.clear();
        super.onCleared();
    }


}
