package com.example.fabian.tinf15b4_lsmf.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.fabian.tinf15b4_lsmf.*;
import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbGenres;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.tools.HttpTools;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.support.v4.app.Fragment;

import android.view.*;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;


public class Fragment_Recommended extends Fragment {





    ListView recommendedList;
    ArrayList<MovieInfo> recommendedMovies = new ArrayList<MovieInfo>();
    MovieListAdapter adapter;


    public Fragment_Recommended() {


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig){
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
