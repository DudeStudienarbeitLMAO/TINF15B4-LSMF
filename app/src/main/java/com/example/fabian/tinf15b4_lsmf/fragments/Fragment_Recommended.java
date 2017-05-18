package com.example.fabian.tinf15b4_lsmf.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.loadtasks.QueryLoadTask;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.util.ArrayList;


public class Fragment_Recommended extends Fragment {


    private ListView recommendedList;
    private ArrayList<MovieInfo> recommendedMovies = new ArrayList<MovieInfo>();
    private MovieListAdapter adapter;




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        recommendedList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_fragment__recommended, container, false);


        recommendedList = (ListView) rootView.findViewById(R.id.recommendedListView);

        adapter = new MovieListAdapter(getActivity().getApplicationContext(), R.layout.listviewrow);
        recommendedList.setAdapter(adapter);


        adapter.clear();
        adapter.setQuerying(true);

        QueryLoadTask queryTask = new QueryLoadTask(adapter, "Fast and", 1);
        queryTask.execute();

        return rootView;
    }

}
