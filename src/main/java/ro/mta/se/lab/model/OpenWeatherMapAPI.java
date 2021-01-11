package ro.mta.se.lab.model;

public class OpenWeatherMapAPI {
    private static OpenWeatherMapAPI instance = null;
    private static String keyAPI = "d2760c0b8ea18c199892b4d1a3bb8c1f";
    private String cityName = "";
    private String countryName = "";
    private String urlAPI = "";

    private OpenWeatherMapAPI() {
        this.cityName = cityName;
        this.countryName = countryName;
    }

    public static OpenWeatherMapAPI getInstance() {
        if (instance == null) {
            instance = new OpenWeatherMapAPI();
        }
        return instance;
    }

    public String getUrlAPI(String cityName, String countryName) {
        this.cityName = cityName;
        this.countryName = countryName;
        this.urlAPI = "http://api.openweathermap.org/data/2.5/weather?q=" + this.cityName + "," + this.countryName + "&appid=" + keyAPI;
        return urlAPI;
    }
}
