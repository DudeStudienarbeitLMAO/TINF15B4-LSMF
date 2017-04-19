package com.example.fabian.tinf15b4_lsmf.fragments;

import java.util.ArrayList;

import com.example.fabian.tinf15b4_lsmf.*;
import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.support.v4.app.Fragment;

import android.view.*;


public class Fragment_Recommended extends Fragment {


    ListView recommendedList;
    ArrayList<MovieInfo> recommendedMovies = new ArrayList<MovieInfo>();
    MovieListAdapter adapter;


    public Fragment_Recommended() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_fragment__recommended, container, false);


        recommendedList = (ListView) rootView.findViewById(R.id.recommendedListView);

        adapter = new MovieListAdapter(getActivity().getApplicationContext(), R.layout.listviewrow);
        recommendedList.setAdapter(adapter);


        return rootView;
    }

}
