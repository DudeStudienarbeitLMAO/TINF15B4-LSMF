package com.example.fabian.tinf15b4_lsmf.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabian.tinf15b4_lsmf.HelperFunctions;
import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.modells.LRUCache;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import org.w3c.dom.Text;

import java.util.List;

public class movieDetail extends AppCompatActivity {


    MovieInfo movieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView moviePoster = (ImageView) findViewById(R.id.moviePoster);
        TextView title = (TextView) findViewById(R.id.title);
        TextView rating = (TextView) findViewById(R.id.rating);
        TextView genre = (TextView) findViewById(R.id.genre);
        TextView movieDesc = (TextView) findViewById(R.id.movie_desc) ;

        movieInfo = (MovieInfo) getIntent().getSerializableExtra("movieInfo");

        if (movieInfo != null) {
            title.setText(movieInfo.getTitle());


            String ratingScore;



            if(movieInfo.getPopularity() > 10) {
                ratingScore = "10";
            }
            else {
                ratingScore = Float.toString(movieInfo.getPopularity());
            }

            rating.setText(ratingScore + "/10");
            moviePoster.setImageBitmap(LRUCache.getInstance().getCache().get(movieInfo.getPosterPath().substring(1)));

            List<Integer> lg = movieInfo.getGenreIds();
            if (lg != null && lg.size() > 0) {
                try {
                    genre.setText(HelperFunctions.getInstance().getGenreMap("de").get(lg.get(0)) + ", " + movieInfo.getReleaseDate().substring(0, 4));
                } catch (MovieDbException e) {
                    e.printStackTrace();
                }
            }

            movieDesc.setText(movieInfo.getOverview());

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMovieDetail);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        final ImageView btnLike = (ImageView) findViewById(R.id.btn_like);

        //Search for movie in liked movies list as cond
        if (false) {
            //If User already likes movie
            btnLike.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btnLike.setImageResource(android.R.drawable.btn_star_big_off);
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_like) {
                    if (false) {
                        //If User already liked movie
                        //Remove from liked moviees list

                        btnLike.setImageResource(android.R.drawable.btn_star_big_off);
                    } else {
                        //Save to liked movies list

                        btnLike.setImageResource(android.R.drawable.btn_star_big_on);

                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
