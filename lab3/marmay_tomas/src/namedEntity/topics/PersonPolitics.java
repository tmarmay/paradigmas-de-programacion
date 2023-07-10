package namedEntity.topics;

import namedEntity.heuristic.myClasses.Person;

public class PersonPolitics extends Person implements topics {
    private String topic = "Politics ";
    private String subtopic;

    public PersonPolitics(String name, String category, String subtopic) {
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
