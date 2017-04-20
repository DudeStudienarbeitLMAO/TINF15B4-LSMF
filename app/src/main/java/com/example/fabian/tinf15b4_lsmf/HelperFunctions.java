package com.example.fabian.tinf15b4_lsmf;

import android.os.StrictMode;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbGenres;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.tools.HttpTools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fabian on 4/20/17.
 */

public class HelperFunctions {

    static{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    public static HashMap<Integer, String> mapGenres(String language) throws MovieDbException {
        HttpClient httpClient = new DefaultHttpClient();
        TmdbGenres tmdbGenres = new TmdbGenres(QueryLoadTask.apiKey, new HttpTools(httpClient));
        List<Genre> genreList = tmdbGenres.getGenreMovieList(language).getResults();
        HashMap<Integer, String> genreMap = new HashMap<>();

        for(Genre g:genreList) {
            genreMap.put(g.getId(), g.getName());
        }

        return genreMap;
    }


}
