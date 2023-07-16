package com.example.popcinema;

import java.io.Serializable;
import java.net.URL;

public class MovieItem implements Serializable {
    private String title;
    private String description;
    private String actors;
    private String genre;
    private String year;
    private String director;
    private String imageURL;

    private long rating = -1;

    public MovieItem(String title, String description, String actors, String genre, String year, String director, String imageURL){
        this.title = title;
        this.description = description;
        this.actors = actors;
        this.genre = genre;
        this.year = year;
        this.director = director;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getActors() {
        return actors;
    }

    public String getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public long getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
