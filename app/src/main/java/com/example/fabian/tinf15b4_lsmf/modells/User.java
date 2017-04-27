package com.example.fabian.tinf15b4_lsmf.modells;

import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private ArrayList<MovieInfo> MovieList;
    private String UserName;
    private String PasswordHash;
    private String EMail;

    public User(String userName, String passwordHash, String email) {
        this.UserName = userName;
        this.PasswordHash = passwordHash;
        this.EMail = email;
    }

    public ArrayList<MovieInfo> getMovieList() {
        return MovieList;
    }

    public void setMovieList(ArrayList<MovieInfo> movieList) {
        MovieList = movieList;
    }

    public void addMovie(MovieInfo movie) {
        MovieList.add(movie);
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }
}
