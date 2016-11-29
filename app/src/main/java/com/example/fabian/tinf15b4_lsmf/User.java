package com.example.fabian.tinf15b4_lsmf;

import java.util.ArrayList;

public class User {

    private  ArrayList<Movie> MovieList;
    private  String UserName;
    private  String PasswordHash;
    private  String EMail;

    public User(String userName, String passwordHash, String email){
        this.UserName = userName;
        this.PasswordHash = passwordHash;
        this.EMail = email;
    }

    public  ArrayList<Movie> getMovieList() {
        return MovieList;
    }

    public  void setMovieList(ArrayList<Movie> movieList) {
        MovieList = movieList;
    }

    public void addMovie(Movie movie){ MovieList.add(movie);}

    public  String getUserName() {
        return UserName;
    }

    public  void setUserName(String userName) {
        this.UserName = userName;
    }

    public  String getEMail() {
        return EMail;
    }

    public  void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }
}
