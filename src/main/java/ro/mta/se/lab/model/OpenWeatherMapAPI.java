package ro.mta.se.lab.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

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

    public String getJSONResponse(String cityName, String countryName) throws IOException {
        String jsonResponse = "";

        URL apiURL = new URL(this.getUrlAPI(cityName, countryName));

        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();

        if (code != 200) {
            throw new RuntimeException("HTTP GET Response code was: " + code);
        } else {
            Scanner scanner = new Scanner(apiURL.openStream());
            while (scanner.hasNextLine()) {
                jsonResponse += scanner.nextLine();
            }
            scanner.close();
        }
        return jsonResponse;
    }
}