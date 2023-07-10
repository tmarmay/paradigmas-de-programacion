package namedEntity.heuristic.myClasses;

import java.io.Serializable;

public class Title  implements Serializable{
    private String canonical;
    private String profession;

    public Title(String profession, String canonical) {
        this.canonical = canonical;
        this.profession = profession;
    }

    public String getCanonical() {
        return this.canonical;
    }
    
    public void setCanonical(String canonical) {
        this.canonical = canonical;
    }
    
    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
