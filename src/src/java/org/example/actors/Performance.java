package org.example.actors;


public class Performance {
    public String title;

    public String type;

    public Performance() { }

    public Performance(String title, String type) {
        this.title = title;
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                "}\n";
    }
}
