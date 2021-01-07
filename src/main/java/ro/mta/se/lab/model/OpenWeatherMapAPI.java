package ro.mta.se.lab.model;

public class OpenWeatherMapAPI {
    private static OpenWeatherMapAPI instance = null;
    private static String keyAPI = "d2760c0b8ea18c199892b4d1a3bb8c1f";
    private String cityName = "";
    private String urlAPI = "";

    private OpenWeatherMapAPI(String cityName) {
        this.cityName = cityName;
        this.urlAPI = "http://api.openweathermap.org/data/2.5/weather?q=" + this.cityName + "&appid=" + keyAPI;
    }

    public static OpenWeatherMapAPI getInstance(String cityName) {
        if (instance == null) {
            instance = new OpenWeatherMapAPI(cityName);
        }
        return instance;
    }

    public String getUrlAPI() {
        return urlAPI;
    }
}
