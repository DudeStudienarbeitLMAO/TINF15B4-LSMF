package com.example.fabian.tinf15b4_lsmf.loadtasks;

import android.os.AsyncTask;

import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.modells.LikedMovieCache;
import com.example.fabian.tinf15b4_lsmf.interfaces.LoadingTaskFinishedListener;
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
    private MovieListAdapter adapter = null;
    private int nextMovie = 0;
    private List<LoadingTaskFinishedListener> listeners;

    public static final int SCROLL_LIKES_ADDING_SIZE = 20;

    public MovieLoadTask(MovieListAdapter adapter, List<Integer> movieIDs, int nextMovie) {
        this.nextMovie = nextMovie;
        this.movieIDs = movieIDs;
        this.adapter = adapter;
        this.listeners = new ArrayList<>();

    }

    public int getNextMovie() {
        return nextMovie;
    }

    @Override
    protected List<MovieInfo> doInBackground(Integer... params) {
        HttpClient httpClient = new DefaultHttpClient();
        TmdbMovies apiMovies = new TmdbMovies(QueryLoadTask.apiKey, new HttpTools(httpClient));

        LikedMovieCache cache = LikedMovieCache.getInstance();

        List<MovieInfo> result = new ArrayList<>();
        MovieInfo movie = null;

        int moviesSizeToAdd = SCROLL_LIKES_ADDING_SIZE;
        int querySizeLeft = movieIDs.size() - nextMovie;


        if (querySizeLeft < moviesSizeToAdd) {
            moviesSizeToAdd = querySizeLeft;
        }

        for (int i = 0; i < moviesSizeToAdd; i++) {
            int movieID = movieIDs.get(i + nextMovie);
            movie = cache.getCache().get(movieID);
            if (movie == null) {

                try {
                    movie = apiMovies.getMovieInfo(movieID, "de", null);
                    cache.saveMovieToCache(movie.getId(), movie);
                } catch (MovieDbException e) {
                    //If exception gets caught e.g. invalid movie id, just ignore and skip?
                    e.printStackTrace();
                    continue;
                }
            }


        }

        nextMovie += moviesSizeToAdd;


        return result;
    }

    @Override
    protected void onPostExecute(List<MovieInfo> result) {
        super.onPostExecute(result);
        if (result != null && result.size() > 0) {
            for (MovieInfo movie : result) {
                adapter.add(movie);
            }
        }

        adapter.setQuerying(false);
        fireLoadingTaskFinished();

    }

    public void addLoadingTaskFinishedListener(LoadingTaskFinishedListener listener) {
        listeners.add(listener);
    }

    private void fireLoadingTaskFinished() {
        for (LoadingTaskFinishedListener listener : listeners) {
            listener.loadingTaskFinished();
        }
    }

}
