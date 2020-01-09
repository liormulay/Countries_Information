package com.example.countries_information;

import androidx.lifecycle.ViewModel;

import com.example.countries_information.models.Country;
import com.example.countries_information.network.NetworkClient;
import com.example.countries_information.network.NetworkInterface;
import com.google.common.base.Strings;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CountriesViewModel extends ViewModel {
    private BehaviorSubject<List<Country>> allCountriesBehaviorSubject = BehaviorSubject.create();
    private List<Country> originalCountries;
    private CompositeDisposable onClearedDispose = new CompositeDisposable();

    public CountriesViewModel() {
        onClearedDispose.add(getAllCountriesFromApi()
                .subscribe(countries -> {
                    originalCountries = countries;
                    allCountriesBehaviorSubject.onNext(originalCountries);
                }));
    }

    public Observable<List<Country>> getAllCountriesFromApi() {
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
                .filter(country -> !Strings.isNullOrEmpty(country.getArea()))
                .sorted((country1, country2) -> {
                    double area1 = Double.parseDouble(country1.getArea());
                    double area2 = Double.parseDouble(country2.getArea());
                    return isDescended ? Double.compare(area2,area1) : Double.compare(area1,area2);
                })
                .toList();

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
