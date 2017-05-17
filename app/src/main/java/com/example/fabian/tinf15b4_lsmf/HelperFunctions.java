package com.example.fabian.tinf15b4_lsmf;

import android.os.StrictMode;

import com.example.fabian.tinf15b4_lsmf.loadtasks.QueryLoadTask;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbGenres;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.tools.HttpTools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fabian on 4/20/17.
 */

public class HelperFunctions {

    private static HelperFunctions instance;
    private HashMap<Integer, String> genreMap;
    private String mapKey;

    static {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static HelperFunctions getInstance() {

        if (instance == null) {

            instance = new HelperFunctions();
        }

        return instance;

    }


    //Get instance to save retrieved Genre mapping, so we dont have to pull again

    private HashMap<Integer, String> mapGenres(String language) throws MovieDbException {
        HttpClient httpClient = new DefaultHttpClient();
        TmdbGenres tmdbGenres = new TmdbGenres(QueryLoadTask.apiKey, new HttpTools(httpClient));
        List<Genre> genreList = tmdbGenres.getGenreMovieList(language).getResults();
        HashMap<Integer, String> genreMap = new HashMap<>();

        for (Genre g : genreList) {
            genreMap.put(g.getId(), g.getName());
        }

        this.mapKey = language;
        this.genreMap = genreMap;

        return genreMap;
    }

    public HashMap<Integer, String> getGenreMap(String language) throws MovieDbException {
        if (genreMap != null && language.equals(this.mapKey)) {
            return genreMap;
        } else {

            return mapGenres(language);


        }

    }


}
