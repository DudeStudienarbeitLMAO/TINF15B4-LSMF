package com.example.fabian.tinf15b4_lsmf;

/**
 * Created by s.gerhardt on 30.05.2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> howSearchMovie = new ArrayList<String>();
        howSearchMovie.add("In the main tab screen click on the movie screen symbol at the right top, fill the opening search bar and press enter");

        List<String> howAddMovie = new ArrayList<String>();
        howAddMovie.add("Click on a movie and hit the greyed out star");

        List<String> howRemoveMovie = new ArrayList<String>();
        howRemoveMovie.add("Click on a movie and hit the lightened star");

        List<String> ratingMovies = new ArrayList<String>();
        ratingMovies.add("We get our data from TMDb, which differs from the IMDb ratings");

        List<String> slowLoading = new ArrayList<String>();
        slowLoading.add("Try turning off the loading of pictures in the settings to save bandwidth and increase loading speed");

        expandableListDetail.put("How do i search for a movie by name?", howSearchMovie);
        expandableListDetail.put("How to add a movie to my watched list?", howAddMovie);
        expandableListDetail.put("How to remove a movie from my watched list?", howRemoveMovie);
        expandableListDetail.put("Why are your ratings different from the IMDb ratings?", ratingMovies);
        expandableListDetail.put("Why does it take so long to load my movie lists?", slowLoading);

        return expandableListDetail;
    }
}