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
import android.widget.ProgressBar;

import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.activities.MainActivity;
import com.example.fabian.tinf15b4_lsmf.activities.MovieDetailsActivity;
import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.loadtasks.RecommendationLoadTask;
import com.example.fabian.tinf15b4_lsmf.interfaces.LoadingTaskFinishedListener;
import com.example.fabian.tinf15b4_lsmf.interfaces.MovieLikesChangedListener;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Recommended extends Fragment implements MovieLikesChangedListener, LoadingTaskFinishedListener {
    private ListView recommendedList;
    private RecommendationLoadTask loadTask;
    private List<Integer> likedMovieIDs = new ArrayList<>();
    private MovieListAdapter adapter;
    private boolean listViewNeedsUpdate = false;
    private ProgressBar progressSpinner;


    @Override
    public void onResume() {
        super.onResume();
        if (listViewNeedsUpdate && adapter != null) {
            //Since liked movies were changed, we have to reload recommendations
            likedMovieIDs = MainActivity.session.getUser().getLikedMovies();

            adapter.clear();
            adapter.setQuerying(true);
            loadTask = new RecommendationLoadTask(adapter, likedMovieIDs);
            loadTask.addLoadingTaskFinishedListener(this);

            progressSpinner.setVisibility(View.VISIBLE);
            loadTask.execute();

            listViewNeedsUpdate = false;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        likedMovieIDs = MainActivity.session.getUser().getLikedMovies();

        MainActivity.session.addMovieLikesChangedListener(this);

        View rootView = inflater.inflate(R.layout.fragment_fragment__recommended, container, false);

        progressSpinner = (ProgressBar) rootView.findViewById(R.id.progressBarRecommended);

        recommendedList = (ListView) rootView.findViewById(R.id.recommendedListView);

        adapter = new MovieListAdapter(getActivity().getApplicationContext(), R.layout.movie_list_view_row);


        recommendedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieInfo movieInfo = (MovieInfo) adapter.getItem(i);
                Intent inte = new Intent(getActivity().getApplicationContext(), MovieDetailsActivity.class);
                inte.putExtra("movieInfo", movieInfo);
                startActivity(inte);
            }

        });


        recommendedList.setAdapter(adapter);
        recommendedList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (recommendedList != null && view.getId() == recommendedList.getId() && recommendedList.getChildCount() > 0) {

                    //If we reach bottom of the list
                    if (!adapter.isQuerying() && recommendedList.getLastVisiblePosition() == recommendedList.getAdapter().getCount() - 1 &&
                            recommendedList.getChildAt(recommendedList.getChildCount() - 1).getBottom() <= recommendedList.getHeight()) {

                            adapter.setQuerying(true);
                            loadTask.extendRecommendations();



                    }

                }
            }
        });


        adapter.clear();
        adapter.setQuerying(true);

        loadTask = new RecommendationLoadTask(adapter, likedMovieIDs);
        loadTask.addLoadingTaskFinishedListener(this);

        progressSpinner.setVisibility(View.VISIBLE);

        loadTask.execute();

        return rootView;
    }


    @Override
    public void movieLikesChanged() {
        listViewNeedsUpdate = true;
    }

    @Override
    public void loadingTaskFinished() {
        progressSpinner.setVisibility(View.GONE);
    }
}
