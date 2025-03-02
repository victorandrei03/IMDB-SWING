package org.example.productions;

import org.example.actors.Actor;
import org.example.Rating;

import java.util.List;

public abstract class Production implements Comparable {
    public String title;
    public String type;
    List<String> directors;
    List<String> actors;
    List<Genre> genres;
    public List<Rating> ratings;
    String plot;
    Double averageRating;
    public Production(){}
    public Production(String name_production, List<Genre> genres, List<String> directors, List<String> actors,
                      List<Rating> ratings, String description, Double mean, String type){
        this.actors = actors;
        this.title = name_production;
        this.genres = genres;
        this.averageRating = mean;
        this.ratings = ratings;
        this.plot = description;
        this.directors = directors;
        this.type = type;
    }
    public int compareTo(Object o) {
        if (o instanceof Production p) {
            return title.compareTo(p.title);
        }
        else if (o instanceof Actor a) {
            return title.compareTo(a.name);
        }

        return 0;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String getPlot() {
        return plot;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        String directors_str = "\nDirectors:\n";
        for (String s : directors){
            directors_str += s + "\n";
        }
        directors_str += "\n";
        String genres_str = "Genres:\n";
        for (Genre g : genres){
            genres_str += g.toString() + "\n";
        }
        genres_str += "\n";
        String actors_str = "Actors:\n";
        for (String s : actors){
            actors_str += s + "\n";
        }
        actors_str += "\n";

        String ratings_str = "Ratings:\n";
        for (Rating r : ratings){
            ratings_str += r.toString() + "\n";
        }

        String return_value = "----------------------------------------------------------------------------\n\n";
        return_value += "Title: " + title + "\nType: " + type + "\n" + directors_str + actors_str + genres_str +
                ratings_str + "Plot: " + plot + "\nAverageRating: " + averageRating + "\n";
        return return_value;
    }
}
