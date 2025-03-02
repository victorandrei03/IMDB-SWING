package org.example.productions;

public class Episode {
    String episodeName;
    String duration;
    public  Episode(){
    }
    public Episode(String name, String time){
        this.episodeName = name;
        this.duration = time;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getEpisodeName(){
        return episodeName;
    }
    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "episodeName='" + episodeName + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
