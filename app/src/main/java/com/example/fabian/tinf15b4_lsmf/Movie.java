package com.example.fabian.tinf15b4_lsmf;

import java.util.ArrayList;

public class Movie {
    private int movieID;
    private ArrayList<Integer> genreIDs;
    private ArrayList<Integer> actorIDs;

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
}
