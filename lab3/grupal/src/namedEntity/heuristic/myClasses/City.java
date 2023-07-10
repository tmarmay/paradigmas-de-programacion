package namedEntity.heuristic.myClasses;

import java.io.Serializable;

public class City implements Serializable{
    private Country country;
    private String capital;
    private int poblation;

    public City(Country country, String capital, int poblation) {
        this.country = country;
        this.capital = capital;
        this.poblation = poblation;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCapital() {
        return this.capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public int getPoblation() {
        return this.poblation;
    }

    public void setPoblation(int poblation) {
        this.poblation = poblation;
    }
}
