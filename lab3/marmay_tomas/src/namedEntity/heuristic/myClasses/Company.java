package namedEntity.heuristic.myClasses;

import namedEntity.NamedEntity;

public class Company extends NamedEntity {
    private String company;

    public Company(String name, String category, String company) {
        super(name, category, 1);
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
