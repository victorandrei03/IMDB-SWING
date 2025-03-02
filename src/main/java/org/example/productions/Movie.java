package org.example.productions;

import org.example.Rating;

import java.util.List;

public class Movie extends Production {
    Long releaseYear;
    String duration;

    public Movie(){
    }
    public Movie(Long year_release, String duration, String title, List<Genre> genre, List<String> name_directors, List<String> name_actors,
                 List<Rating> rating, String description, Double mean, String type){
        super(title, genre, name_directors, name_actors, rating, description, mean, type);
        this.releaseYear = year_release;
        this.duration = duration;
        this.genres = genre;
    }

    public String getDuration() {
            return duration;
    }

    public List<Genre> getGenres() {
        return super.genres;
    }

    public Long getReleaseYear() {
        return releaseYear;
    }

    public String getTitle() {
        return title;
    }


    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString() + "RealeaseYear: " + releaseYear + "\n\nDuration: " + duration + "\n\n";
    }
}
