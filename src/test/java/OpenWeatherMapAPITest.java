import org.junit.*;
import org.junit.rules.ExpectedException;
import ro.mta.se.lab.model.OpenWeatherMapAPI;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Clasa folosita pentru testare unitara cu ajutorul JUnit
 * Vom testa comportamentul clasei independete OpenWeatherMapAPI,
 * responsabila cu tot procesul de interactiune cu API-ul celor de la
 * OpenWeatherMap
 *
 * @author: Stoica Gabriel
 */
public class OpenWeatherMapAPITest {

    public OpenWeatherMapAPI api;
    private String keyAPI = "d2760c0b8ea18c199892b4d1a3bb8c1f";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        api = OpenWeatherMapAPI.getInstance();
    }

    /**
     * Functie care testeaza daca URL-ul ce urmeaza a fi
     * folosit pentru a obtine datele vremii din Buzau, Romania,
     * si Brasov, Romania,
     * este construit corect in cadrul functiei getUrlAPI() din cadrul
     * clasei OpenWeatherMapAPI.
     */
    @Test
    public void cityCountry() {
        String urlAPI = api.getUrlAPI("Buzau", "RO");
        if (!urlAPI.equals("http://api.openweathermap.org/data/2.5/weather?q=Buzau,RO&appid=" + keyAPI)) {
            Assert.fail();
        }

        String urlAPI2 = api.getUrlAPI("Brasov", "RO");
        if (!urlAPI2.equals("http://api.openweathermap.org/data/2.5/weather?q=Brasov,RO&appid=" + keyAPI)) {
            Assert.fail();
        }
    }
    /**
     * Functie care testeaza daca URL-ul ce urmeaza a fi
     * folosit pentru a obtine datele vremei din Ramnicu Sarat, Romania,
     * este construit corect in cadrul functiei getUrlAPI() din cadrul
     * clasei OpenWeatherMapAPI.
     */
    @Test
    public void cityCountry2(){
        String urlAPI = api.getUrlAPI("Ramnicu Sarat", "RO");
        if (!urlAPI.equals("http://api.openweathermap.org/data/2.5/weather?q=Ramnicu Sarat,RO&appid=" + keyAPI)) {
            Assert.fail();
        }
    }

    /**
     * Functie care testeaza daca raspunsul primit de la serverul
     * OpenWeatherMap in urma cererii GET este invalid
     * Se foloseste intentionat un nume de oras incorect
     * pentru a demonstra comportamentul corect al functiei getJSONResponse()
     */
    @Test
    public void throwsIOException() throws IOException {
        String cityName = "Ramnicu Sarat123";
        String countryName = "RO";

        exception.expect(RuntimeException.class);
        exception.expectMessage("HTTP GET Response code was: 404");

        api.getJSONResponse(cityName, countryName);
    }
}