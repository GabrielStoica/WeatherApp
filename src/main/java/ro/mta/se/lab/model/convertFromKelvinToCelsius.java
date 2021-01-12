package ro.mta.se.lab.model;

import ro.mta.se.lab.interfaces.convertFromXToX;

import java.text.DecimalFormat;

public class convertFromKelvinToCelsius implements convertFromXToX {
    private double KelvinDegree = 0;
    private String CelsiusDegree = "";

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

    @Override
    public double convertDegree(String degree, double scale) {
        KelvinDegree = Float.parseFloat(degree);
        KelvinDegree -= scale;

        return KelvinDegree;
    }

    @Override
    public boolean verify(String my_degree, String degree, double scale) {
        if (degree.equals(getXDegree(degree, scale)))
            return true;
        return false;
    }
}
