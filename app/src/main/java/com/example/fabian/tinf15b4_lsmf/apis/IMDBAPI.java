package com.example.fabian.tinf15b4_lsmf.apis;

import com.example.fabian.tinf15b4_lsmf.modells.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabian on 4/5/17.
 */

public class IMDBAPI {

    private static final String apiKey = "a58333d7dddf6bcc826dfaed7c49f20e";


    public static Movie getMovieByID(String id){
        Movie result = new Movie();

        //Use IMDB Api to get Movie-Information

        return  result;
    }


    public List<Movie> searchMovieByQuery(String query, List<Movie> alreadywachted){
        ArrayList<Movie> result = new ArrayList<Movie>();

        return result;
    }


    public List<Movie> searchMovieByAttribute(List<Integer> actorids, List<Integer> genreids, List<Movie> alreadywachted){
        ArrayList<Movie> result = new ArrayList<Movie>();

        return result;
    }


    public List<Movie> searchMovieByActor(List<Integer> actorids,List<Movie> alreadywachted){
        ArrayList<Movie> result = new ArrayList<Movie>();

        return result;
    }


    public  List<Movie> searchMovieByGenre(List<Integer> genreids, List<Movie> alreadywachted){
        ArrayList<Movie> result = new ArrayList<Movie>();

        return result;
    }
}
