package com.example.fabian.tinf15b4_lsmf.modells;

import java.util.ArrayList;

public class Movie {
    private int movieID;
    private ArrayList<Integer> genreIDs;
    private ArrayList<Integer> actorIDs;
    private String title;
    private String posterURL;
    private String genre;
    private String description;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    private double rating;

    public Movie(){}

    public Movie(int movieID, ArrayList<Integer> genreIDs, ArrayList<Integer> actorIDs) {
        this.movieID = movieID;
        this.genreIDs = genreIDs;
        this.actorIDs = actorIDs;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public ArrayList<Integer> getGenreIDs() {
        return genreIDs;
    }

    public void setGenreIDs(ArrayList<Integer> genreIDs) {
        this.genreIDs = genreIDs;
    }

    public ArrayList<Integer> getActorIDs() {
        return actorIDs;
    }

    public void setActorIDs(ArrayList<Integer> actorIDs) {
        this.actorIDs = actorIDs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
