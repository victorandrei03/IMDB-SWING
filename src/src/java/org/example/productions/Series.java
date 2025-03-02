package org.example.productions;

import org.example.Rating;

import java.util.List;
import java.util.Map;

public class Series extends Production {
    int numSeasons;
    Long releaseYear;
    private Map<String, List<Episode>> seasons;
    public Series(){}
    public Series(int numSeasons, Long releaseYear, Map<String, List<Episode>> seasons,
                  String name_production, List<Genre> genre, List<String> name_directors, List<String> name_actors,
                  List<Rating> rating, String description, Double mean, String type) {
        super(name_production, genre, name_directors, name_actors, rating, description, mean, type);
        this.numSeasons = numSeasons;
        this.releaseYear = releaseYear;
        this.seasons = seasons;
        this.type = type;
    }

    public void setSeasons(Map<String, List<Episode>> seasons) {
        this.seasons = seasons;
    }

    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Long getReleaseYear() {
        return releaseYear;
    }

    public int getNumSeasons() {
        return numSeasons;
    }


    public Map<String, List<Episode>> getSeasons() {
        return seasons;
    }

    public void setNumSeasons(Integer numSeasons) {
        this.numSeasons = numSeasons;
    }

    @Override
    public String toString() {
        String seasons_str = "";
        for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
            seasons_str += "\n" + entry.getKey() + "\n";
            List<Episode> epis = seasons.get(entry.getKey());
            for (Episode e : epis){
                seasons_str += "    " + e + "\n";
            }
        }

        return super.toString() + "Seasons number: " + numSeasons + "\n\nSeasons:" + seasons_str + "\nRealeaseYear: " +
                releaseYear + "\n\n";
    }

}
