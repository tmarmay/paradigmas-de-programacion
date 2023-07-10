package namedEntity.heuristic.myClasses;

public class Surname {
    private String origin;
    private String canonical;

    public Surname(String origin, String canonical) {
        this.origin = origin;
        this.canonical = canonical;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCanonical() {
        return this.canonical;
    }

    public void setCanonical(String canonical) {
        this.canonical = canonical;
    }
}
