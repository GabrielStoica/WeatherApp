package ro.mta.se.lab.interfaces;

public interface convertFromXToX {
    public String getXDegree(String degree, double scale);
    public double convertDegree(String degree, double scale);
    boolean verify(String my_degree, String degree, double scale);
}
