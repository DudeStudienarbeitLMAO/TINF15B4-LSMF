package com.example.fabian.tinf15b4_lsmf.modells;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String userName;
    private String passwordHash;
    private String eMail;
    private List<Integer> likedMovies = new ArrayList<>();

    public User(String userName, String passwordHash, String email) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.eMail = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<Integer> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(List<Integer> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public void addLikedMovie(int movieID) {
        this.likedMovies.add(movieID);
    }

    public void removeLikedMovie(int movieID) {
        this.likedMovies.remove((Object) movieID);
    }

}
