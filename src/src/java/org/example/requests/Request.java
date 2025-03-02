package org.example.requests;

import org.example.observer.Observer;
import org.example.observer.Subject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Request implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private RequestType type;
    private Date createdDate;
    public String actorName;
    public String movieTitle;
    public String description;
    public String username;
    public String to;

    public Request() {}

    public Request(RequestType type, Date createdDate, String actorName, String movieTitle, String description, String username, String to) {
        this.type = type;
        this.createdDate = createdDate;
        this.actorName = actorName;
        this.movieTitle = movieTitle;
        this.description = description;
        this.username = username;
        this.to = to;
    }

    public RequestType getType() {
        return type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getActorName() {
        return actorName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public String getTo() {
        return to;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void clearObservers(){
        observers.clear();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTo(String to) {
        this.to = to;
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
        for (Observer o : observers){
            o.update(notification);
        }
    }

    @Override
    public String toString() {
        String str;
        if (this.type == RequestType.MOVIE_ISSUE){
            str = "Username: " + username + "\nTo: " + to + "\nMovie Title: " + movieTitle + "\nDescription: "
                    + description + "\nDate: " + createdDate + "\nType: " + type + "\n";
        }
        else if (this.type == RequestType.ACTOR_ISSUE){
            str = "Username: " + username + "\nTo: " + to + "\nActor name: " + actorName + "\nDescription: "
                    + description + "\nDate: " + createdDate + "\nType: " + type + "\n";
        }
        else{
            str = "Username: " + username + "\nTo: " + to + "\nDescription: "  + description + "\nDate: "
                    + createdDate + "\nType: " + type + "\n";
        }
        return str;
    }
}
