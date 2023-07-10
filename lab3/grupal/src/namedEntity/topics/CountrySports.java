package namedEntity.topics;

import namedEntity.heuristic.myClasses.Country;

public class CountrySports extends Country implements topics {
    private String topic = "Sports ";
    private String subtopic;
    
    public CountrySports(String name, String category, String subtopic) {
        super(name, category);
        this.subtopic = subtopic;
    }

    public String getTopic() {
        return this.topic + this.subtopic;
    }
    
    public void setTopic(String subtopic) {
        this.subtopic = subtopic;
    }
}
