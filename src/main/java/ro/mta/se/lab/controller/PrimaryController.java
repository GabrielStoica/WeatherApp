package ro.mta.se.lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Country;
import ro.mta.se.lab.model.OpenWeatherMapAPI;
import ro.mta.se.lab.model.convertFromKelvinToCelsius;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Clasa controller responsabila cu gestionarea actiunilor din cadrul interfetei grafice
 * Face legatura intre toate clasele prezentei aplicatii
 *
 * @author: Stoica Gabriel
 *
 */

public class PrimaryController implements Initializable {

    convertFromKelvinToCelsius convert = new convertFromKelvinToCelsius();
    private HashMap<Country, ArrayList<City>> locationMap;
    private ArrayList<City> citiesToAdd;
    private String descriptionText = "";
    private String degreeText = "";
    private String humidityText = "";
    private String precipitationText = "";
    private String dayTimeText = "";
    private String iconText = "";
    private String windText = "";
    private Long sunriseLong;
    private Long sunsetLong;
    private String sunriseText;
    private String sunsetText;
    float KelvinDegree = 0;

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
    @FXML
    private Label degree1;
    @FXML
    private ImageView weatherIcon;
    @FXML
    private Label sunset;
    @FXML
    private Label sunrise;

    public PrimaryController(HashMap<Country, ArrayList<City>> locationMap) {

        this.locationMap = new HashMap<>();
        this.locationMap =  locationMap;
    }

    /**
     * Functie apelata initial, responsabila cu initializarea
     * ferestrei initiale
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        country.setOnAction(this::handleCountry);
        city.setOnAction(this::handleCity);

        country.setValue("Selectare țară");
        city.setValue("Selectare oraș");

        for (Country index : locationMap.keySet()) {
            country.getItems().add(index.getCountryName());
        }

        cityName.setVisible(false);
        showButton.setDisable(true);
        degree1.setVisible(false);
        city.setDisable(true);


    }

    /**
     * Functie responsabila cu gestionarea actiunii aparute
     * in urma selectiei tarii
     *
     * @param actionEvent
     */
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

    /**
     * Functie responsabila cu gestionarea actiunii aparute
     * in urma selectiei orasului
     *
     * @param actionEvent
     */
    @FXML
    private void handleCity(ActionEvent actionEvent) {
        //country.getItems().clear();
        //country.setValue("Selectare țară");
        showButton.setDisable(false);

    }

    /**
     * Functie responsabila cu gestionarea actiunii aparute
     * in urma apsarii butonului de "Afisare vreme"
     * Se instantiaza clasa OpenWeatherAPI, in urma caruia se face
     * un request de tip GET pentru a obtine informatiile codificate
     * sub forma .json
     * In final cu ajutorul functiei parseJson() se parcurge continutul
     * raspunsului primit prin intermediul API-ului si se actualizeaza
     * campurile din interfata grafica
     *
     * @param actionEvent
     */
    @FXML
    public void handleShowButton(ActionEvent actionEvent) {

        OpenWeatherMapAPI api = OpenWeatherMapAPI.getInstance();
        //System.out.println(api.getUrlAPI(city.getValue(), country.getValue()));

        try {

            String jsonResponse = api.getJSONResponse(city.getValue(), country.getValue());
            setCity(city.getValue());
            degree1.setVisible(true);
            parseJson(jsonResponse);
            updateLabels();
            logInFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Functie responsabila cu logarea istoricului
     * afisarilor.
     * In fisierul "log.txt" se vor stoca numele tarii, locatia si
     * ora la care a fost cautata vremea.
     * @throws IOException
     */
    private void logInFile() throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("[HH:mm:ss - dd/MM/yyyy]");
        LocalDateTime now = LocalDateTime.now();

        String textToAppend = " S-a afisat temperatura pentru orasul " + city.getValue() + " din " + country.getValue() + "\n";

        BufferedWriter writer = new BufferedWriter(
                new FileWriter("log.txt", true));
        writer.write("[ " + dtf.format(now) + " ]");
        writer.write(textToAppend);

        writer.close();
    }

    /**
     * Functie responsabila cu actualizarea
     * Label-urilor si ImageView-ului din cadrul
     * interfetei grafice
     *
     * @throws IOException
     */
    @FXML
    private void updateLabels() throws IOException {
        description.setText(descriptionText);
        humidity.setText("Umiditate: " + humidityText + "%");
        if (KelvinDegree > 0) {
            System.out.println("greater");
            degree.setLayoutX(193);
        } else {
            degree.setLayoutX(153);
        }

        // degree1.setLayoutX(cityName+5);

        degree.setText(degreeText);
        wind.setText("Vânt: " + windText + " km/h");
        precipitation.setText("Precipitații: " + precipitationText + "%");
        dayTime.setText(dayTimeText);
        weatherIcon.setImage(downloadIcon(iconText));
        sunrise.setText("Soarele răsare la: " + sunriseText);
        sunset.setText("Soarele apune la: " + sunsetText);
    }

    /**
     * Functie responsabila cu prelucarea raspunsului
     * de tip .json primit in urma apelului GET la serverul
     * OpenWeatherMap
     *
     * @param jsonBuffer
     * @throws ParseException
     */
    private void parseJson(String jsonBuffer) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(jsonBuffer);

        JSONArray weather = (JSONArray) json.get("weather");

        JSONObject weatherObj = (JSONObject) weather.get(0);
        descriptionText = weatherObj.get("description").toString();
        iconText = weatherObj.get("icon").toString();

        JSONObject main = (JSONObject) json.get("main");
        humidityText = main.get("humidity").toString();
        degreeText = convert.getXDegree(main.get("temp").toString(), 273.15);
        //degreeText = convertKelvinToCelsius(main.get("temp").toString());

        JSONObject wind = (JSONObject) json.get("wind");
        windText = wind.get("speed").toString();

        JSONObject clouds = (JSONObject) json.get("clouds");
        precipitationText = clouds.get("all").toString();

        Long dt = (Long) json.get("dt");
        Long timezone = (Long) json.get("timezone");
        dayTimeText = convertUnixTimeToRealTime(dt, timezone);

        JSONObject sys = (JSONObject) json.get("sys");
        sunriseLong = (Long) sys.get("sunrise");
        sunsetLong = (Long) sys.get("sunset");

        sunriseText = convertUnixTimeToRealTime(sunriseLong, timezone);
        sunsetText = convertUnixTimeToRealTime(sunsetLong, timezone);

    }

    /**
     * Functie responsabila de descarcarea icon-ului specific
     * tipului de vreme
     *
     * @param iconName numele icon-ului
     * @return ImageView img returneaza un obiect de tip Image
     * @throws IOException
     */
    private Image downloadIcon(String iconName) throws IOException {
        URL imageURL = new URL("http://openweathermap.org/img/wn/" + iconName + "@2x.png");
        System.out.println("http://openweathermap.org/img/wn/" + iconName + "@2x.png");

        InputStream in = new BufferedInputStream(imageURL.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(iconName + ".png"));

        for (int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();

        Image img = new Image("file:" + iconName + ".png");
        return img;
    }

    /**
     * Functie DE TEST responsabila cu conversia gradelor Kelvin
     * in grade Celsius pentru a putea fi afisate corespunzator
     * in cadrul interfei grafice
     *
     * @param degree
     * @return boolean
     */

    public boolean convertKelvinToCelsius(String degree) {
        String testDegree = convert.getXDegree(degree, 273.15);

        DecimalFormat df = new DecimalFormat("0");
        KelvinDegree = Float.parseFloat(degree);
        KelvinDegree -= 273.15;

        String CelsiusDegree = String.valueOf(df.format(KelvinDegree));
        System.out.println(CelsiusDegree);
        if (CelsiusDegree.equals("-0")) {
            CelsiusDegree = "0";
        }

        if (CelsiusDegree.equals(testDegree))
            return true;
        else return false;
    }

    /**
     * Functie responsabila cu conversia timestamp-ului
     * zonei geografice specific fiecarei locatii
     *
     * @param timezone
     * @return
     */
    public int countHoursTimezone(Long timezone) {
        int countHours = 1;
        while (timezone != 0) {
            countHours++;
            timezone /= 3600;
        }
        return countHours;
    }

    /**
     * Functie responsabila cu conversia timestamp-ului
     * orei curente la care a fost facuta solicitarea de tip GET
     * catre serverul OpenWeatherMap
     * @param dayTime
     * @param timezone
     * @return
     */
    private String convertUnixTimeToRealTime(Long dayTime, Long timezone) {

        int countHours = countHoursTimezone(timezone);

        Date date = new Date(dayTime * 1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("EEEE HH:mm aaa");

        if (timezone > 0) {
            jdf.setTimeZone(TimeZone.getTimeZone("GMT" + "+" + countHours));
        } else jdf.setTimeZone(TimeZone.getTimeZone("GMT" + "-" + countHours));
        String realDayTime = jdf.format(date);

        return realDayTime;

    }

    private void setCity(String city) {
        cityName.setVisible(true);
        cityName.setText(city);
    }


    public boolean verify(String my_degree, String degree, double scale) {
        if (my_degree.equals(convert.getXDegree(degree, scale)))
            return true;
        return false;
    }
}
