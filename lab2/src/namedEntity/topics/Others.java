package namedEntity.topics;

import namedEntity.NamedEntity;

public class Others extends NamedEntity implements topics {
    private String topic = "Others";
    
    public Others(String name) {
        super(name, "Others", 1);
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}