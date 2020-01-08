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
                .sorted((country1, country2) ->
                        compareArea(Double.valueOf(country1.getArea()), Double.valueOf(country2.getArea()), isDescended))
                .toList();

    }

    private int compareArea(Double area1, Double area2, boolean isDescended) {
        if (area1 < area2) {
            if (isDescended) {
                return 1;
            }
            return -1;
        }

        if (area2 < area1) {
            if (isDescended) {
                return -1;
            }
            return 1;
        }
        return 0;
    }

    public void notifySortByArea(boolean isDescended) {
        onClearedDispose.add(sortByArea(isDescended)
                .subscribe(countries -> allCountriesBehaviorSubject.onNext(countries)));
    }

    @Override
    protected void onCleared() {
        onClearedDispose.clear();
        super.onCleared();
    }


}
