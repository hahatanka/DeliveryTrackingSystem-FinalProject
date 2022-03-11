package com.DeliveryTrackingSystem.DeliveryTrackingSystem.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("singleton")
public class CountryService {

    private List<String> countriesList = new ArrayList<>();

    CountryService(){
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {

            Locale country = new Locale("", countryCode);
            countriesList.add(country.getDisplayCountry(Locale.ENGLISH));
        }
        Collections.sort(countriesList);
    }

    public List<String> getCountriesList() {

        return countriesList;
    }}

