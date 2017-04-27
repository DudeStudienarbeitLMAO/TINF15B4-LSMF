package com.example.fabian.tinf15b4_lsmf.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fabian.tinf15b4_lsmf.R;


public class Fragment_Viewed extends Fragment {
    public Fragment_Viewed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__viewed, container, false);
    }


}
