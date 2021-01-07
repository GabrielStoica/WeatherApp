package ro.mta.se.lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Country;
import ro.mta.se.lab.model.OpenWeatherMapAPI;

import java.io.OptionalDataException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class PrimaryController implements Initializable {

    private HashMap<Country, ArrayList<City>> locationMap;
    private ArrayList<City> citiesToAdd;
    private String jsonResponse = "";
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> city;
    @FXML
    private Button showButton;
    @FXML
    private Label dayTime;
    @FXML
    private Label description;
    @FXML
    private Label wind;
    @FXML
    private Label humidity;
    @FXML
    private Label precipitation;
    @FXML
    private Label degree;
    @FXML
    private Label cityName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        country.setOnAction(this::handleCountry);
        city.setOnAction(this::handleCity);
        country.setValue("Selectare țară");
        for (Country index : locationMap.keySet()) {
            country.getItems().add(index.getCountryName());
        }
        city.setDisable(true);
        city.setValue("Selectare oraș");

        showButton.setDisable(true);

    }

    @FXML
    private void handleCountry(ActionEvent actionEvent) {
        city.getItems().clear();
        city.setValue("Selectare oraș");

        String countrySelected = country.getValue();
        for (Country index : locationMap.keySet()) {
            if (index.getCountryName().equals(countrySelected)) {
                citiesToAdd = locationMap.get(index);
                for (int count = 0; count < citiesToAdd.size(); count++) {
                    city.getItems().add(citiesToAdd.get(count).getCityName());
                }
            }
        }
        city.setDisable(false);

    }

    @FXML
    private void handleCity(ActionEvent actionEvent) {
        //country.getItems().clear();
        //country.setValue("Selectare țară");
        showButton.setDisable(false);

    }

    @FXML
    private void handleShowButton(ActionEvent actionEvent) {
        System.out.println("click");
        setCity(city.getValue());

        OpenWeatherMapAPI api = OpenWeatherMapAPI.getInstance(city.getValue());
        System.out.println(api.getUrlAPI());

        try {
            URL url = new URL(api.getUrlAPI());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("HTTP GET Response code was: " + code);
            } else {
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNextLine()) {
                    jsonResponse += scanner.nextLine();
                }
                scanner.close();
                System.out.println(jsonResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setCity(String city) {
        cityName.setText(city);
    }

    public PrimaryController(HashMap<Country, ArrayList<City>> locationMap) {
        this.locationMap = locationMap;
    }
}
