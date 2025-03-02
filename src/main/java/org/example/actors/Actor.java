package org.example.actors;

import org.example.productions.Production;

import java.util.ArrayList;
import java.util.List;

public class Actor implements Comparable {
    public String name;
    public List<Performance> performances = new ArrayList<>();
    public String biography;

    public Actor(){}

    public Actor(String name, String biography, List<Performance> performances){
        this.name = name;
        this.performances = performances;
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Object o) {
        if (o instanceof Actor ac) {
            return this.name.compareTo(ac.name);
        }
        else if (o instanceof Production p) {
            return name.compareTo(p.title);
        }

        return 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (Performance p : performances){
            s += p;
        }
        String result_value = "----------------------------------------------------------------------------------\n\n";
        result_value += "Name: " + name + "\n\nPerformances:\n" + s + "\nBiography: " + biography + "\n";
        return result_value;
    }
}
