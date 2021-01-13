package ro.mta.se.lab.model;

import java.util.List;

/**
 * Clasa responsabila cu retinerea datelor unei oras,
 * dupa parcurgerea fisierului de intrare initial
 *
 *
 * @author: Stoica Gabriel
 */

public class City {

    private String cityName;
    private String latitude;
    private String longitude;
    private String countryName;
    private String ID;

    public City(String cityName, String latitude, String longitude, String countryName, String ID) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryName = countryName;
        this.ID = ID;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }
}

