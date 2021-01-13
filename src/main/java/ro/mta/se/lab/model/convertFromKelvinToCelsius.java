package ro.mta.se.lab.model;

import ro.mta.se.lab.interfaces.convertFromXToX;

import java.text.DecimalFormat;

/**
 * Clasa responsabila care implementeaza interfata
 * convertFromXToX
 * Este responsabila cu conversia gradelor Kelvin in grade
 * Celsius
 *
 * @author: Stoica Gabriel-Marius
 */

public class convertFromKelvinToCelsius implements convertFromXToX {
    private double KelvinDegree = 0;
    private String CelsiusDegree = "";

    /**
     * Functie care realizeaza efectiv conversia din
     * grade Kelvin in grade Celsius
     *
     * @param degree String reprezentand gradele in format Kelvin
     * @param scale double reprezetand cat trebuie scazut pentru a ajunge la gradele Celsius (273.15)
     * @return returneaza String grade Celsius
     */
    @Override
    public String getXDegree(String degree, double scale) {

        this.KelvinDegree = convertDegree(degree, scale);

        DecimalFormat df = new DecimalFormat("0");
        this.CelsiusDegree = String.valueOf(df.format(this.KelvinDegree));


        if (this.CelsiusDegree.equals("-0")) {
            this.CelsiusDegree = "0";
        }
        System.out.println(CelsiusDegree);

        return this.CelsiusDegree;
    }

    /**
     * Functie care realizeaza conversia din String in double
     * a gradelor primite in format Kelvin si scade "scale",
     * pentru a obtine gradele in format Celsius
     * @param degree
     * @param scale
     * @return
     */
    @Override
    public double convertDegree(String degree, double scale) {
        KelvinDegree = Float.parseFloat(degree);
        KelvinDegree -= scale;

        return KelvinDegree;
    }

    /**
     * Functie pentru TESTARE
     *
     * @param my_degree
     * @param degree
     * @param scale
     * @return
     */
    @Override
    public boolean verify(String my_degree, String degree, double scale) {
        if (degree.equals(getXDegree(degree, scale)))
            return true;
        return false;
    }
}
