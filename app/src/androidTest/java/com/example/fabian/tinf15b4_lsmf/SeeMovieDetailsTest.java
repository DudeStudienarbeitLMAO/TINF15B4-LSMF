package com.example.fabian.tinf15b4_lsmf;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fabian.tinf15b4_lsmf.activities.AddMovieActivity;
import com.example.fabian.tinf15b4_lsmf.activities.MainActivity;
import com.example.fabian.tinf15b4_lsmf.activities.MovieDetailsActivity;
import com.example.fabian.tinf15b4_lsmf.apis.Ssapi;
import com.example.fabian.tinf15b4_lsmf.modells.Session;
import com.example.fabian.tinf15b4_lsmf.modells.User;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.fabian.tinf15b4_lsmf.activities.MainActivity.session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SeeMovieDetailsTest {
    @Rule
    public ActivityTestRule<AddMovieActivity> mActivityRule
            = new ActivityTestRule<>(AddMovieActivity.class);

    @BeforeClass
    public static void setUpUser(){
        User user = new User("Tester", "b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d", null);
        Ssapi ssapi = new Ssapi();
        MainActivity.session = new Session(ssapi, user);
    }

    @Before
    public void prepareQueryList() throws InterruptedException {
        onView(withId(R.id.searchBar)).perform(ViewActions.typeText("Harry Potter")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        //Let Query & UI load
        Thread.sleep(1000);
    }

    @Test
    public void useAppContext() {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.fabian.tinf15b4_lsmf", appContext.getPackageName());

    }

    @Test
    public void seeMovieDetails() throws MovieDbException {
        Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(MovieDetailsActivity.class.getName(), null, false);

        final AddMovieActivity addMovieActivity = mActivityRule.getActivity();

        final ListView queryList = (ListView) addMovieActivity.findViewById(R.id.queryListView);

        addMovieActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                queryList.performItemClick(queryList, 0, 0);

            }
        });

        MovieInfo selectedMovie = (MovieInfo) queryList.getAdapter().getItem(0);

        View row = queryList.getChildAt(0);

        row.findViewById(R.id.movieImage);

        String ratingScore;

        if (selectedMovie.getVoteAverage() > 10) {
            ratingScore = "10";
        } else {
            ratingScore = Float.toString(selectedMovie.getVoteAverage()).substring(0, 3);
        }

        String expectedDesc = selectedMovie.getOverview();
        String expectedGenre = HelperFunctions.getInstance().getGenreMap("de").get(selectedMovie.getGenreIds().get(0)) + ", " + selectedMovie.getReleaseDate().substring(0, 4);
        String expectedTitle = selectedMovie.getTitle();
        String expectedRating = ratingScore + "/10";

        MovieDetailsActivity movieDetails = (MovieDetailsActivity) monitor.waitForActivity();

        assertNotNull(movieDetails);

        TextView desc = (TextView) movieDetails.findViewById(R.id.movie_desc);
        TextView title = (TextView) movieDetails.findViewById(R.id.title);
        TextView rating = (TextView) movieDetails.findViewById(R.id.rating);
        TextView genre = (TextView) movieDetails.findViewById(R.id.genre);

        assertEquals(desc.getText(),expectedDesc);
        assertEquals(title.getText(), expectedTitle);
        assertEquals(rating.getText(), expectedRating);
        assertEquals(genre.getText().toString(), expectedGenre);
    }
}
