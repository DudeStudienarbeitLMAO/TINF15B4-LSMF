package com.example.fabian.tinf15b4_lsmf;

import java.util.ArrayList;
import java.util.Locale;
import com.example.fabian.tinf15b4_lsmf.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.*;
import android.support.design.widget.*;


public class Fragment_Recommended extends Fragment {


    ListView recommendedList;
    ArrayList<Movie> recommendedMovies = new ArrayList<Movie>();
    MovieListAdapter adapter;

    Movie m = new Movie();
    public Fragment_Recommended() {
        m.setTitle("Dankest Muvie everr");
        m.setRating(420);
        m.setPosterURL("MEMES");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_fragment__recommended, container, false);


        recommendedList = (ListView) rootView.findViewById(R.id.recommendedListView);
        adapter = new MovieListAdapter(getActivity().getApplicationContext(), R.layout.listviewrow);
        recommendedList.setAdapter(adapter);

        adapter.add(m);
        adapter.add(m);
        adapter.add(m);

        return inflater.inflate(R.layout.fragment_fragment__recommended, container, false);
    }

}
