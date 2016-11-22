package com.example.fabian.tinf15b4_lsmf;

import java.util.ArrayList;

public class User {

    private static ArrayList<Movie> MovieList;
    private static int UserID;
    private static String EMail;

    public static ArrayList<Movie> getMovieList() {
        return MovieList;
    }

    public static void setMovieList(ArrayList<Movie> movieList) {
        MovieList = movieList;
    }

    public static int getUserID() {
        return UserID;
    }

    public static void setUserID(int userID) {
        UserID = userID;
    }

    public static String getEMail() {
        return EMail;
    }

    public static void setEMail(String EMail) {
        User.EMail = EMail;
    }
}
