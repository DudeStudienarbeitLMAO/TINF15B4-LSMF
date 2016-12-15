package com.example.fabian.tinf15b4_lsmf;



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
public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager, getApplicationContext());
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        // mTabLayout.setupWithViewPager(mPager1);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_about:
                Intent i = new Intent(getApplicationContext(), activity_about.class);
                startActivity(i);
                return true;
            case R.id.show_settings:
                Intent r = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(r);
                return true;
            case R.id.show_help:
                Intent k = new Intent(getApplicationContext(), help.class);
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}