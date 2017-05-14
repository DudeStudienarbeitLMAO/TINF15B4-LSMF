package com.example.fabian.tinf15b4_lsmf.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.adapters.PagerAdapter;
import com.example.fabian.tinf15b4_lsmf.apis.Ssapi;
import com.example.fabian.tinf15b4_lsmf.modells.Session;
import com.example.fabian.tinf15b4_lsmf.modells.SsapiResult;
import com.example.fabian.tinf15b4_lsmf.modells.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabLayout;
    public static Session session;

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




        User user = new User("Tester", "b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d", null);
        Ssapi ssapi = new Ssapi();

        session = new Session(ssapi, user);


        SsapiResult result = ssapi.getLikedMovies(user);
        List<Integer> likedMovieIDs = new ArrayList<>();

        if(result != null) {

            for (int i = 0; i < result.getContent().length(); i++) {
                try {
                    likedMovieIDs.add(result.getContent().getInt(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        user.setLikedMovies(likedMovieIDs);

        //loggedInUser = (User) getIntent().getSerializableExtra("currentUser");

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
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.show_settings:
                Intent r = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(r);
                return true;
            case R.id.show_help:
                Intent k = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(k);
                return true;
            case R.id.addViewedMovie:
                Intent s = new Intent(getApplicationContext(), AddMovieActivity.class);
                startActivity(s);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}