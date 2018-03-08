package me.ashu.example.models;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotEmpty
    private List<String> topicsOfInterest;


    public Profile(){
        topicsOfInterest = new ArrayList<>();
    }

    public void addTopics (String category){
        topicsOfInterest.add(category);
    }



    private  String topic;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getTopicsOfInterest() {
        return topicsOfInterest;
    }

    public void setTopicsOfInterest(List<String> topicsOfInterest) {
        this.topicsOfInterest = topicsOfInterest;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
