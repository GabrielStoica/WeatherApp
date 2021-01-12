import org.junit.*;
import org.junit.rules.ExpectedException;
import ro.mta.se.lab.model.OpenWeatherMapAPI;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenWeatherMapAPITest {

    public OpenWeatherMapAPI api;
    private String keyAPI = "d2760c0b8ea18c199892b4d1a3bb8c1f";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        api = OpenWeatherMapAPI.getInstance();
    }

    @Test
    public void cityCountry() {
        String urlAPI = api.getUrlAPI("Buzau", "RO");
        if (!urlAPI.equals("http://api.openweathermap.org/data/2.5/weather?q=Buzau,RO&appid=" + keyAPI)) {
            Assert.fail();
        }
    }

    @Test
    public void cityCountry2(){
        String urlAPI = api.getUrlAPI("Ramnicu Sarat", "RO");
        if (!urlAPI.equals("http://api.openweathermap.org/data/2.5/weather?q=Ramnicu Sarat,RO&appid=" + keyAPI)) {
            Assert.fail();
        }
    }

    @Test
    public void throwsIOException() throws IOException {
        String cityName = "Ramnicu Sarat123";
        String countryName = "RO";

        exception.expect(RuntimeException.class);
        exception.expectMessage("HTTP GET Response code was: 404");

        api.getJSONResponse(cityName, countryName);
    }
}