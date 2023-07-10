package namedEntity.heuristic.myClasses;

import namedEntity.NamedEntity;

public class Country extends NamedEntity {
    private int population;
    private String language;

    public Country(String name, String category) {
        super(name, category, 1);
        this.population = 0;
        this.language = "spanish";
    }

    public int getPopulation() {
        return this.population;
    }

    public void setOrigin(int population) {
        this.population = population;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
