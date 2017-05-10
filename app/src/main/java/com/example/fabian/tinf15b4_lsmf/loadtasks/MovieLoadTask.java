package com.example.fabian.tinf15b4_lsmf.loadtasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fabian.tinf15b4_lsmf.modells.LikedMovieCache;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbMovies;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.tools.HttpTools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s.gerhardt on 10.05.2017.
 */

public class MovieLoadTask extends AsyncTask<Integer, Void, List<MovieInfo>> {
    private List<Integer> movieIDs;
    private List<MovieInfo> result;

    public MovieLoadTask(List<Integer> movieIDs) {
        this.movieIDs = movieIDs;

    }

    @Override
    protected List<MovieInfo> doInBackground(Integer... params) {
        HttpClient httpClient = new DefaultHttpClient();
        TmdbMovies apiMovies = new TmdbMovies(QueryLoadTask.apiKey, new HttpTools(httpClient));

        LikedMovieCache cache = LikedMovieCache.getInstance();

        try {
            result = new ArrayList<>();
            MovieInfo movie = null;

            for (int movieID : movieIDs) {
                movie = apiMovies.getMovieInfo(movieID, "de", null);
                result.add(movie);
                if(movie == null) {
                    Log.i("NULL", "NULL");
                }
                cache.saveMovieToCache(movie.getId(), movie);
            }

        } catch (MovieDbException e) {
            e.printStackTrace();
        }

        return result;
    }
}
