import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import ro.mta.se.lab.controller.PrimaryController;
import ro.mta.se.lab.interfaces.convertFromXToX;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Country;
import ro.mta.se.lab.model.convertFromKelvinToCelsius;

import java.util.ArrayList;
import java.util.HashMap;

public class PrimaryControllerTest {

    convertFromKelvinToCelsius convertor;
    PrimaryController controller;
    private static HashMap<Country, ArrayList<City>> locationMap = new HashMap<Country, ArrayList<City>>();


    @Before
    public void setUp() {
        ArrayList<City> cityList = new ArrayList<City>();
        cityList.add(new City("Ramnicu Sarat", "23", "42", "RO", "68574"));
        cityList.add(new City("Buzau", "30", "42", "RO", "68575"));
        cityList.add(new City("Cluj-Napoca", "35", "41", "RO", "68576"));
        cityList.add(new City("Brasov", "10", "12", "RO", "68577"));

        Country country = new Country("RO");
        locationMap.put(country, cityList);

        convertor = mock(convertFromKelvinToCelsius.class);
        controller = new PrimaryController(locationMap);

    }

    @Test
    public void convertKelvinToCelsius() {
        assertTrue(controller.verify("2", "275", 273.15));
        assertFalse(controller.verify("3", "280", 273.15));
        assertTrue(controller.verify("3", "276", 273.15));
    }

    @Test
    public void verify() {
        when(convertor.getXDegree("275", 273.15)).thenReturn("2");
        controller.convertKelvinToCelsius("275");

        try {
            Mockito.verify(convertor).verify("2", "275", 273.15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}