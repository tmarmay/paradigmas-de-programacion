package namedEntity.heuristic.myClasses;

import java.util.List;

import namedEntity.NamedEntity;

public class Person extends NamedEntity {
    private Surname surname;
    private Name personName;
    private Title title;

    public Person(String name, String category) {
        super(name, category, 1);

        Surname sn = new Surname("-", name);
        Name nm = new Name("-", name, List.of());
        Title tl = new Title(name, "-");
        
        this.surname = sn;
        this.personName = nm;
        this.title = tl;
    }

    public Surname getSurname() {
        return this.surname;
    }

    public void setSurname(Surname surname) {
        this.surname = surname;
    }

    public Name getPersonName() {
        return this.personName;
    }

    public void setPersonName(Name name) {
        this.personName = name;
    }

    public Title getTitle() {
        return this.title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
