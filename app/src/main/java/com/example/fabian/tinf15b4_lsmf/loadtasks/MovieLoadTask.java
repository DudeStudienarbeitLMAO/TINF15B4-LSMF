package com.example.fabian.tinf15b4_lsmf.loadtasks;

import android.os.AsyncTask;

import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
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
    private MovieListAdapter adapter = null;
    private List<MovieInfo> result;
    private int nextMovie = 0;


    private static final int SCROLL_LIKES_ADDING_SIZE = 20;

    public MovieLoadTask(MovieListAdapter adapter, List<Integer> movieIDs, int nextMovie) {
        this.nextMovie = nextMovie;
        this.movieIDs = movieIDs;
        this.adapter = adapter;

    }

    public int getNextMovie() {
        return nextMovie;
    }

    @Override
    protected List<MovieInfo> doInBackground(Integer... params) {
        HttpClient httpClient = new DefaultHttpClient();
        TmdbMovies apiMovies = new TmdbMovies(QueryLoadTask.apiKey, new HttpTools(httpClient));

        LikedMovieCache cache = LikedMovieCache.getInstance();

        try {
            result = new ArrayList<>();
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

                    movie = apiMovies.getMovieInfo(movieID, "de", null);
                    cache.saveMovieToCache(movie.getId(), movie);
                }
                result.add(movie);


            }

            nextMovie += moviesSizeToAdd;

        } catch (MovieDbException e) {
            e.printStackTrace();
        }

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

    }

}
