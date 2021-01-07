package ro.mta.se.lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Country;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {
    private HashMap<Country, ArrayList<City>> locationMap;
    private ArrayList<City> citiesToAdd;
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> city;
    @FXML
    private Button showButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        country.setOnAction(this::handleCountry);
        city.setOnAction(this::handleCity);
        country.setValue("Selectare țară");
        for(Country index: locationMap.keySet()){
                country.getItems().add(index.getCountryName());
        }
        city.setDisable(true);
        city.setValue("Selectare oraș");

        showButton.setDisable(true);

    }

    @FXML
    private void handleCountry(ActionEvent actionEvent){
        city.getItems().clear();
        city.setValue("Selectare oraș");

        String countrySelected = country.getValue();
        for(Country index: locationMap.keySet()){
            if(index.getCountryName().equals(countrySelected)) {
                citiesToAdd = locationMap.get(index);
                for (int count = 0; count < citiesToAdd.size(); count++) {
                    city.getItems().add(citiesToAdd.get(count).getCityName());
                }
            }
        }
        city.setDisable(false);

    }

    @FXML
    private void handleCity(ActionEvent actionEvent){
        //country.getItems().clear();
        //country.setValue("Selectare țară");

        showButton.setDisable(false);


    }

    @FXML
    private void handleShowButton(ActionEvent actionEvent){
        System.out.println("click");

    }

    public PrimaryController(HashMap<Country, ArrayList<City>> locationMap) {
        this.locationMap = locationMap;
    }
}
