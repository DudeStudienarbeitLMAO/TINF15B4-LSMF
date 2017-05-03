package com.example.fabian.tinf15b4_lsmf.adapters;

/**
 * Created by Fabian on 21.10.2016.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.fragments.Fragment_Recommended;
import com.example.fabian.tinf15b4_lsmf.fragments.Fragment_Viewed;


public class PagerAdapter extends FragmentStatePagerAdapter {
    Context context;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new Fragment_Recommended();
                break;
            case 1:
                frag = new Fragment_Viewed();
                break;

        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = context.getResources().getString(R.string.recommended);
                break;
            case 1:
                title = context.getResources().getString(R.string.viewed);
                break;

        }

        return title;
    }
}
