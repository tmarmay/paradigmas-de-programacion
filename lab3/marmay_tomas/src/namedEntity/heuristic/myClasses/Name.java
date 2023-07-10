package namedEntity.heuristic.myClasses;

import java.io.Serializable;
import java.util.List;

public class Name implements Serializable {
    private String origin;
    private String canonical;
    private List<String> variations;

    public Name(String origin, String canonical, List<String> variations) {
        this.origin = origin;
        this.canonical = canonical;
        this.variations = variations;
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

    public List<String> getVariations() {
        return this.variations;
    }

    public void setVariations(List<String> variations) {
        this.variations = variations;
    }
}
