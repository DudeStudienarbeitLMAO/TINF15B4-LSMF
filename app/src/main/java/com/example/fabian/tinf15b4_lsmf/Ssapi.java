package com.example.fabian.tinf15b4_lsmf;

import java.util.ArrayList;

public class Ssapi {

    public static boolean testConnection(User user){

        return true;
    }

    public static boolean addMovie(User user, Movie movie){
        user.addMovie(movie);

        // webrequest an db

        return false;
    }

    public static boolean registerUser(User user){
        // TODO
        return false;
    }

    public static ArrayList<Movie> fetchMovielist(User user){
        // TODO
        return null;
    }

    public static boolean resetPassword(String email){

        return false;
    }
}
