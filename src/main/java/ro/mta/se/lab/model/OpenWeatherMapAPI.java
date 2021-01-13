package ro.mta.se.lab.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Clasa de tip singleton responsabila cu gestionarea API-ului OpenWeatherMap
 * Realizeaza apelul de tip GET
 * Formeaza string-ul ce va fi utilizat in cadrul acestui apel
 * Stocheaza intr-un obiect de tip String, continutul raspunsului de tip json
 *
 * @author: Stoica Gabriel-Marius
 */
public class OpenWeatherMapAPI {
    private static OpenWeatherMapAPI instance = null;
    private static String keyAPI = "d2760c0b8ea18c199892b4d1a3bb8c1f";
    private String cityName = "";
    private String countryName = "";
    private String urlAPI = "";

    /**
     * Constructorul clasei
     */
    private OpenWeatherMapAPI() {
        this.cityName = cityName;
        this.countryName = countryName;
    }

    /**
     * Functie care implementeaza comportamentul
     * singleton al clasei
     *
     * @return instanta clasei
     */
    public static OpenWeatherMapAPI getInstance() {
        if (instance == null) {
            instance = new OpenWeatherMapAPI();
        }
        return instance;
    }

    /**
     * Functie responsabila cu formarea string-ului ce urmeaza
     * a fi folosit in apelul de tip GET catre serverul OpenWeatherMap
     *
     * @param cityName numele orasului
     * @param countryName numele tarii
     * @return adresa la care trebuie sa se faca cererea de tip GET
     */
    public String getUrlAPI(String cityName, String countryName) {
        this.cityName = cityName;
        this.countryName = countryName;
        this.urlAPI = "http://api.openweathermap.org/data/2.5/weather?q=" + this.cityName + "," + this.countryName + "&appid=" + keyAPI;
        return urlAPI;
    }

    /**
     * Functie responsabila cu realizarea cererii de tip GET
     * catre serverul OpenWeatherMap
     * Totodata, converteste si raspunsul de tip json, primit de la server
     * intr-un String
     *
     * @param cityName numele orasului
     * @param countryName numele tarii
     * @return String cu continutul raspunsului in urma apelului GET
     * @throws IOException
     */
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