package com.example.fabian.tinf15b4_lsmf.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.fabian.tinf15b4_lsmf.QueryLoadTask;
import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.modells.Movie;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

import java.util.ArrayList;


/**
 * Created by s.gerhardt on 07.04.2017.
 */


public class AddMovie extends AppCompatActivity {
    QueryLoadTask queryTask;

    static {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddMovie);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        final ListView queryList = (ListView) findViewById(R.id.queryListView);

        final MovieListAdapter adapter = new MovieListAdapter(getApplicationContext(), R.layout.listviewrow);
        queryList.setAdapter(adapter);
        queryList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (queryList != null && view.getId() == queryList.getId() && queryList.getChildCount() > 0) {


                    //If we reach bottom of the list
                    if (queryList.getLastVisiblePosition() == queryList.getAdapter().getCount() - 1 &&
                            queryList.getChildAt(queryList.getChildCount() - 1).getBottom() <= queryList.getHeight()) {
                        if (!adapter.isQuerying()) {
                            adapter.setQuerying(true);
                            String query = queryTask.getQuery();
                            int nextPage = queryTask.getNextPage();

                            //Extend list with next Page results
                            queryTask = new QueryLoadTask(adapter, query, nextPage);
                            queryTask.execute();
                        }

                    }
                }
            }
        });


        final SearchView searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setIconifiedByDefault(false);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {


                if (!adapter.isQuerying()) {
                    adapter.clear();
                    adapter.setQuerying(true);

                    queryTask = new QueryLoadTask(adapter, query, 1);
                    queryTask.execute();


                }
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
