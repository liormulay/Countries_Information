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
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CountriesViewModel extends ViewModel {
    /**
     * emit {@link List<Country>} it will emit list every time that there is updated list <br>
     */
    private BehaviorSubject<List<Country>> allCountriesBehaviorSubject = BehaviorSubject.create();
    /**
     * the original countries that come from api <br>
     * key - the alpha three code of the country <br>
     * value - the country
     */
    private Map<String, Country> originalCountries = new HashMap<>();
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
                    initOriginalCountries(countries);
                    allCountriesBehaviorSubject.onNext(ImmutableList.copyOf(originalCountries.values()));
                }, Throwable::printStackTrace));
    }

    private void initOriginalCountries(List<Country> countries) {
        for (Country country : countries) {
            originalCountries.put(country.getAlpha3Code(), country);
        }
    }

    /**
     * Call when you want to start {@link BordersActivity}
     *
     * @param countryListPair contains {@link Country} and {@link List<Country>} of
     *                        the countries that have border with it
     */
    private void startBordersActivity(Pair<Country, List<Country>> countryListPair) {
        Intent intent = new Intent(context, BordersActivity.class);
        intent.putExtra(COUNTRY_EXTRA, countryListPair.first);
        ArrayList<Country> countries = new ArrayList<>(countryListPair.second);
        intent.putExtra(BORDERS_EXTRA, countries);
        context.startActivity(intent);
    }

    /**
     * @param country that need to find the countries that have border with it
     * @return {@link List<Country>} of countries that have border with given param
     */
    private List<Country> findBorders(Country country) {
        List<String> bordersCodes = country.getBorders();
        List<Country> borders = new ArrayList<>();
        for (String borderCode : bordersCodes) {
            borders.add(originalCountries.get(borderCode));
        }
        return borders;
    }

    /**
     * @return {@link List<Country>} that come from api
     */
    private Single<List<Country>> getAllCountriesFromApi() {
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getAllCountries()
                .subscribeOn(Schedulers.io());
    }

    /**
     * @return {@link Observable} that emit {@link List<Country>} of updated countries
     */
    public Observable<List<Country>> getAllCountries() {
        return allCountriesBehaviorSubject.hide();
    }

    /**
     * sort the counties according their area size<br>
     * countries that have no area will be at the end of list
     *
     * @param isDescended determine if the order need to be descended
     * @return {@link Single} that emit the sorted list
     */
    private Single<List<Country>> sortByArea(boolean isDescended) {
        return Observable.fromIterable(originalCountries.values())
                .subscribeOn(Schedulers.io())
                .sorted((country1, country2) -> sortByArea(country1.getArea(), country2.getArea(), isDescended))
                .toList();

    }

    /**
     * Determine which area need to come first
     *
     * @param area1String area of first country
     * @param area2String area of second country
     * @param isDescended determine if the order need to be descended
     * @return int number that say who need to come first (0 says that it doesn't matter)
     */
    private int sortByArea(String area1String, String area2String, boolean isDescended) {
        double area1 = getDoubleArea(area1String, isDescended);
        double area2 = getDoubleArea(area2String, isDescended);
        return isDescended ? Double.compare(area2, area1) : Double.compare(area1, area2);

    }

    /**
     * parse the string of area to double
     *
     * @param areaString  that need to be parse
     * @param isDescended determine in case that the string is null or empty <br>
     *                    if returned value will be 0 or positive infinity
     * @return the parsed value
     */
    private double getDoubleArea(String areaString, boolean isDescended) {
        return Strings.isNullOrEmpty(areaString) ?
                isDescended ? 0 : Double.POSITIVE_INFINITY
                : Double.valueOf(areaString);
    }

    /**
     * sort the counties according their name in english
     *
     * @param isDescended determine if the order need to be descended
     * @return {@link Single} that emit the sorted list
     */
    private Single<List<Country>> sortByName(boolean isDescended) {
        return Observable.fromIterable(originalCountries.values())
                .subscribeOn(Schedulers.io())
                .sorted((country1, country2) -> isDescended ? country2.getName().compareTo(country1.getName())
                        : country1.getName().compareTo(country2.getName()))
                .toList();

    }

    /**
     * notify that need to sort the countries by their area
     *
     * @param isDescended determine if the order need to be descended
     */
    public void notifySortByArea(boolean isDescended) {
        onClearedDispose.add(sortByArea(isDescended)
                .subscribe(countries -> allCountriesBehaviorSubject.onNext(countries)));
    }

    /**
     * notify that need to sort the countries by their name in english
     *
     * @param isDescended determine if the order need to be descended
     */
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
