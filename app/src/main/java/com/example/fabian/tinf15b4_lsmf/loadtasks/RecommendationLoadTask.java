package com.example.fabian.tinf15b4_lsmf.loadtasks;

import android.graphics.Movie;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.interfaces.LoadingTaskFinishedListener;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbMovies;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.tools.HttpTools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by s.gerhardt on 20.05.2017.
 */

public class RecommendationLoadTask extends AsyncTask<List<Integer>, Void, List<MovieInfo>> {
    private List<Integer> movieIDs;
    private List<MovieInfo> result;
    private int nextMovie = 0;
    private MovieListAdapter adapter = null;
    private List<LoadingTaskFinishedListener> listeners;
    private static final int SCROLL_RECOMMENDATIONS_ADDING_SIZE = 20;


    public RecommendationLoadTask(MovieListAdapter adapter, List<Integer> movieIDs) {
        this.movieIDs = movieIDs;
        this.adapter = adapter;
        this.listeners = new ArrayList<>();

    }

    @Override
    protected List<MovieInfo> doInBackground(List<Integer>... params) {
        HttpClient httpClient = new DefaultHttpClient();
        TmdbMovies apiMovies = new TmdbMovies(QueryLoadTask.apiKey, new HttpTools(httpClient));

        List<MovieInfo> recommendedMovies = new ArrayList<>();
        HashMap<Integer, Integer> rankingMap = new HashMap<>();

        for (int movieID : movieIDs) {
            try {
                for (MovieInfo recommendedMovie : apiMovies.getSimilarMovies(movieID, 1, "de").getResults()) {
                    Log.i("Recommended movie is", recommendedMovie.getTitle());
                    if (!movieIDs.contains(recommendedMovie.getId())) {

                        int recommendationCount = 0;
                        if (rankingMap.containsKey(recommendedMovie.getId())) {

                            recommendationCount = rankingMap.get(recommendedMovie.getId());
                            Log.i("Was already in list", Integer.toString(recommendationCount));
                        } else {
                            recommendedMovies.add(recommendedMovie);
                        }
                        rankingMap.put(recommendedMovie.getId(), recommendationCount + 1);
                    }
                }
            } catch (MovieDbException e) {
                e.printStackTrace();
            }
        }

        result = evaluateRanking(rankingMap, recommendedMovies);

        return result;
    }

    private List<MovieInfo> evaluateRanking(HashMap<Integer, Integer> rankingMap, List<MovieInfo> recommendedMovies) {
        List<MovieInfo> result = new ArrayList<>();

        Integer[] sortedRecommendedMovieIDs = sortIntoArray(rankingMap);

        for (int movieID : sortedRecommendedMovieIDs) {
            for (MovieInfo recommendedMovie : recommendedMovies) {
                if (recommendedMovie.getId() == movieID) {
                    result.add(recommendedMovie);
                    recommendedMovies.remove(recommendedMovie);
                    Log.i(recommendedMovie.getTitle(), recommendedMovie.getTitle());
                    Log.i(Integer.toString(rankingMap.get(recommendedMovie.getId())), Integer.toString(rankingMap.get(recommendedMovie.getId())));
                    break;
                }
            }
        }

        return result;
    }

    private Integer[] sortIntoArray(HashMap<Integer, Integer> rankingMap) {
        Integer[] recommendedMovieIDs = rankingMap.keySet().toArray(new Integer[0]);

        //Sort
        int temp;
        for (int i = 1; i < recommendedMovieIDs.length; i++) {
            for (int j = 0; j < recommendedMovieIDs.length - i; j++) {
                if (rankingMap.get(recommendedMovieIDs[j]) < rankingMap.get(recommendedMovieIDs[j + 1])) {
                    temp = recommendedMovieIDs[j];
                    recommendedMovieIDs[j] = recommendedMovieIDs[j + 1];
                    recommendedMovieIDs[j + 1] = temp;
                }

            }
        }

        return recommendedMovieIDs;
    }

    @Override
    protected void onPostExecute(List<MovieInfo> result) {
        super.onPostExecute(result);

        extendRecommendations();

        fireLoadingTaskFinished();

    }

    public void extendRecommendations() {
        if (result != null && result.size() > 0) {
            int moviesSizeToAdd = SCROLL_RECOMMENDATIONS_ADDING_SIZE;
            int querySize = result.size() - nextMovie;

            if (querySize < moviesSizeToAdd) {
                moviesSizeToAdd = querySize;
            }
            for (int i = 0; i < moviesSizeToAdd; i++) {
                adapter.add(result.get(i + nextMovie));
            }
            nextMovie += moviesSizeToAdd;
        }

        adapter.setQuerying(false);

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
