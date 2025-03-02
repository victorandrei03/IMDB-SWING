package org.example;

import org.example.observer.Observer;
import org.example.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class Rating implements Subject {
    List<Observer> observers = new ArrayList<>();
    String username;
    int rating;
    String comment;
    public Rating(){

    }
    public Rating(String username, int rating, String comment){
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String notification) {
        if (observers.isEmpty()) {
            return;
        }
        for (Observer o : observers){
            o.update(notification);
        }
    }

    @Override
    public String toString() {
        return "Username: " + this.username + "\nRating: " + this.rating + "\nComment" + this.comment + "\n";
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
}
