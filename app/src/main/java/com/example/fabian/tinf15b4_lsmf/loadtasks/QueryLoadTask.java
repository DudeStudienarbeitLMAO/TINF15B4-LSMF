package com.example.fabian.tinf15b4_lsmf.loadtasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbGenres;
import com.omertron.themoviedbapi.methods.TmdbSearch;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.HttpTools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by s.gerhardt on 10.04.2017.
 */

public class QueryLoadTask extends AsyncTask<String, Void, ResultList<MovieInfo>> {

    public static final String apiKey = "a58333d7dddf6bcc826dfaed7c49f20e";
    MovieListAdapter adapter = null;
    ResultList<MovieInfo> result = null;
    int nextMovie = 0;
    String query = "";
    int nextPage = 1;


    private static final int SCROLL_QUERY_ADDING_SIZE = 20;

    public QueryLoadTask(MovieListAdapter adapter, String query, int nextPage) {
        this.adapter = adapter;
        this.query = query;
        this.nextPage = nextPage;

    }

    public String getQuery() {
        return this.query;
    }

    public int getNextPage(){
        return this.nextPage;
    }

    @Override
    protected ResultList<MovieInfo> doInBackground(String... params) {
        HttpClient httpClient = new DefaultHttpClient();


        TmdbSearch apiSearch = new TmdbSearch(apiKey, new HttpTools(httpClient));


        try {

            result = apiSearch.searchMovie(query, nextPage, "de", true, null, null, null);
            nextPage++;

        } catch (MovieDbException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ResultList<MovieInfo> result) {
        super.onPostExecute(result);
        if (result != null && result.getTotalResults() > 0) {
            extendQueryView();
        }

        adapter.setQuerying(false);

    }

    public void extendQueryView () {
        int moviesSizeToAdd = SCROLL_QUERY_ADDING_SIZE;
        int querySizeLeft = result.getResults().size() - nextMovie;


        if (querySizeLeft < moviesSizeToAdd) {
            moviesSizeToAdd = querySizeLeft;
        }

        Log.i("Pages count: " + Integer.toString(result.getTotalPages()), Integer.toString(result.getTotalPages()));
        Log.i("Result size: " + Integer.toString(result.getResults().size()), Integer.toString(result.getResults().size()));
        Log.i("Query Left: " + Integer.toString(querySizeLeft), Integer.toString(querySizeLeft));
        Log.i("Size to Add: " + Integer.toString(moviesSizeToAdd), Integer.toString(moviesSizeToAdd));
        Log.i("Next movie: " + Integer.toString(nextMovie), Integer.toString(nextMovie));

        for (int i = nextMovie; i < nextMovie + moviesSizeToAdd; i++) {

            MovieInfo movieInfo = result.getResults().get(i);
            Log.i(Integer.toString(i), Integer.toString(i));


            adapter.add(movieInfo);



        }
        nextMovie += moviesSizeToAdd;

    }


}
