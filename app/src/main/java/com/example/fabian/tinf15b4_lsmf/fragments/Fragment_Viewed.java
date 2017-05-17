package com.example.fabian.tinf15b4_lsmf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.activities.MainActivity;
import com.example.fabian.tinf15b4_lsmf.activities.MovieDetailsActivity;
import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.loadtasks.MovieLoadTask;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Viewed extends Fragment {
    private ListView likedMoviesList;
    private MovieLoadTask loadTask;
    private List<Integer> likedMovieIDs = new ArrayList<>();
    private MovieListAdapter adapter;


    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.session.getUser().wasMovieListChanged() && adapter != null) {
            //Since movieset was changed, we have to reload all movies (indexes are different)
            likedMovieIDs = MainActivity.session.getUser().getLikedMovies();

            adapter.clear();
            adapter.setQuerying(true);
            loadTask = new MovieLoadTask(adapter, likedMovieIDs, 0);
            loadTask.execute();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        likedMovieIDs = MainActivity.session.getUser().getLikedMovies();

        View rootView = inflater.inflate(R.layout.fragment_fragment__viewed, container, false);


        likedMoviesList = (ListView) rootView.findViewById(R.id.likedListView);

        adapter = new MovieListAdapter(getActivity().getApplicationContext(), R.layout.listviewrow);



        likedMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieInfo movieInfo = (MovieInfo) adapter.getItem(i);
                //implement movie details here
                Intent inte = new Intent(getActivity().getApplicationContext(), MovieDetailsActivity.class);
                inte.putExtra("movieInfo", movieInfo);
                startActivity(inte);
            }

        });


        likedMoviesList.setAdapter(adapter);
        likedMoviesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (likedMoviesList != null && view.getId() == likedMoviesList.getId() && likedMoviesList.getChildCount() > 0) {

                    //If we reach bottom of the list
                    if (likedMoviesList.getLastVisiblePosition() == likedMoviesList.getAdapter().getCount() - 1 &&
                            likedMoviesList.getChildAt(likedMoviesList.getChildCount() - 1).getBottom() <= likedMoviesList.getHeight()) {
                        if (!adapter.isQuerying()) {
                            adapter.setQuerying(true);
                            int nextMovie = loadTask.getNextMovie();

                            //Extend list with next Page results
                            loadTask = new MovieLoadTask(adapter, likedMovieIDs, nextMovie);
                            loadTask.execute();
                        }

                    }
                }
            }
        });


        adapter.clear();
        adapter.setQuerying(true);

        loadTask = new MovieLoadTask(adapter, likedMovieIDs, 0);
        loadTask.execute();

        return rootView;
    }





}
