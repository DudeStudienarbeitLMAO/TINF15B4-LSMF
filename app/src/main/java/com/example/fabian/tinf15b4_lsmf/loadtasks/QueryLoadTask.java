package com.example.fabian.tinf15b4_lsmf.loadtasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbSearch;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.HttpTools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by s.gerhardt on 10.04.2017.
 */

public class QueryLoadTask extends AsyncTask<String, Void, ResultList<MovieInfo>> {

    public static final String apiKey = "a58333d7dddf6bcc826dfaed7c49f20e";
    private MovieListAdapter adapter = null;
    private String query = "";
    private int nextPage = 1;

    public QueryLoadTask(MovieListAdapter adapter, String query, int nextPage) {
        this.adapter = adapter;
        this.query = query;
        this.nextPage = nextPage;

    }

    public String getQuery() {
        return this.query;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    @Override
    protected ResultList<MovieInfo> doInBackground(String... params) {
        HttpClient httpClient = new DefaultHttpClient();
        TmdbSearch apiSearch = new TmdbSearch(apiKey, new HttpTools(httpClient));

        ResultList<MovieInfo> result = null;


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
            for (MovieInfo movie : result.getResults()) {
                Log.i(movie.getTitle(), movie.toString());
                adapter.add(movie);
            }
        }

        adapter.setQuerying(false);

    }


}
