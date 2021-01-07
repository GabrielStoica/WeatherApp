package ro.mta.se.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.PrimaryController;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Country;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    private static Scene scene;
    private String input_lineBuffer = "";
    private String[] lineBuffer;
    private static String inputFilename = "input_data.txt";

    private static HashMap<Country, ArrayList<City>> locationMap = new HashMap<Country, ArrayList<City>>();
    private ArrayList<Country> countries = new ArrayList<Country>();
    private String countryName;
    private String cityName;
    private String latitude;
    private String longitude;
    private String id;


    @Override
    public void start(Stage stage) throws IOException {
        initCountryCityData();
        scene = new Scene(loadFXML("mainPage"), 640, 480);
        stage.setTitle("Aplicație verifică vremea");
        stage.setScene(scene);
        stage.show();
    }

    private void initCountryCityData() throws FileNotFoundException {
        File inFile = new File(inputFilename);
        Scanner readFromFile = new Scanner(inFile);
        int first_line = 0;

        while (readFromFile.hasNextLine()) {
            input_lineBuffer = readFromFile.nextLine();
            lineBuffer = input_lineBuffer.split("&");

            id = lineBuffer[0];
            cityName = lineBuffer[1];
            latitude = lineBuffer[2];
            longitude = lineBuffer[3];
            countryName = lineBuffer[4];

            City newCity = new City(cityName, latitude, longitude, countryName, id);
            Country newCountry = new Country(countryName);

            int countryExists = 0;

            for (Country index : locationMap.keySet()) {
                if (index.getCountryName().equals(countryName)) {
                    countryExists = 1;
                }
            }

            if (countryExists == 0) {
                locationMap.put(newCountry, new ArrayList<City>());
            }

            for (Country index : locationMap.keySet()) {
                if (index.getCountryName().equals(countryName)) {
                    locationMap.get(index).add(newCity);
                }
            }


        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/view/" + fxml + ".fxml"));
        fxmlLoader.setController(new PrimaryController(locationMap));

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}