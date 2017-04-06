package com.example.fabian.tinf15b4_lsmf.fragments;

import java.util.ArrayList;

import com.example.fabian.tinf15b4_lsmf.*;
import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.modells.Movie;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.support.v4.app.Fragment;

import android.view.*;


public class Fragment_Recommended extends Fragment {


    ListView recommendedList;
    ArrayList<Movie> recommendedMovies = new ArrayList<Movie>();
    MovieListAdapter adapter;


    public Fragment_Recommended() {

        Movie m = new Movie();
        m.setTitle("The Dark Knight");
        m.setGenre("Action");
        m.setRating(9.8);
        m.setPosterURL("https://img.yescdn.ru/2016/03/06/poster/ac0758b25745450b2388dd19adb59d14-batman-the-dark-knight.jpg");
        recommendedMovies.add(m);

        Movie w = new Movie();
        w.setTitle("Dr. Strange");
        w.setGenre("Action");
        w.setRating(8.8);
        w.setPosterURL("http://www.closeup.de/media/oart_0/oart_d/oart_73332/thumbs/744926_1821522.jpg");
        recommendedMovies.add(w);

        Movie s = new Movie();
        s.setTitle("The Purge 3");
        s.setGenre("Horror");
        s.setRating(5.7);
        //s.setPosterURL("http://de.web.img1.acsta.net/newsv7/16/05/20/15/50/304292.jpg");
        recommendedMovies.add(s);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_fragment__recommended, container, false);


        recommendedList = (ListView) rootView.findViewById(R.id.recommendedListView);

        adapter = new MovieListAdapter(getActivity().getApplicationContext(), R.layout.listviewrow);
        recommendedList.setAdapter(adapter);

        for (Movie m :
             recommendedMovies) {
            adapter.add(m);
        }

        return rootView;
    }

}
