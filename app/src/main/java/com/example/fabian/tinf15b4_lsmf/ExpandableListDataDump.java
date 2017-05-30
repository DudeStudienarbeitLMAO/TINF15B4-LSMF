package com.example.fabian.tinf15b4_lsmf;

/**
 * Created by s.gerhardt on 30.05.2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataDump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> howAddMovie = new ArrayList<String>();
        howAddMovie.add("Click on movie and click the greyed out star");

        List<String> howRemoveMovie = new ArrayList<String>();
        howRemoveMovie.add("Click on movie and click the lightend star");

        expandableListDetail.put("How to add a movie to my watched list?", howAddMovie);
        expandableListDetail.put("How to remove a movie from my watched list?", howRemoveMovie);
        return expandableListDetail;
    }
}