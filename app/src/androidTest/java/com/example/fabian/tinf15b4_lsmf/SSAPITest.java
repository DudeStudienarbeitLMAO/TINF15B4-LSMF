package com.example.fabian.tinf15b4_lsmf;

import com.example.fabian.tinf15b4_lsmf.adapters.MovieListAdapter;
import com.example.fabian.tinf15b4_lsmf.apis.Ssapi;
import com.example.fabian.tinf15b4_lsmf.modells.Session;
import com.example.fabian.tinf15b4_lsmf.modells.SsapiResult;
import com.example.fabian.tinf15b4_lsmf.modells.User;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by fabian on 5/18/17.
 */

public class SSAPITest {

    private static Session testSession;
    private static final int testMovieID = 235543;
    private static MovieInfo testMovieInfo;

    @BeforeClass
    public static void setUp(){
        User user = new User("Tester", "b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d", null);
        Ssapi ssapi = new Ssapi();
        testSession = new Session(ssapi, user);

        testMovieInfo  = new MovieInfo();
        testMovieInfo.setId(testMovieID);
    }



    @Test
    public void likeMovie(){
        testSession.insertLikedMovie(testMovieInfo);

        JSONArray moviesInDB = testSession.getSsapi().getLikedMovies(testSession.getUser()).getContent();

        assertTrue(testSession.getUser().getLikedMovies().contains(testMovieID));
        assertTrue(moviesInDB.toString().contains("\""+ testMovieID + "\""));
    }

    @Test
    public void removeMovie(){
        likeMovie();

        testSession.removeLikedMovie(testMovieInfo);

        JSONArray moviesInDB = testSession.getSsapi().getLikedMovies(testSession.getUser()).getContent();

        assertFalse(testSession.getUser().getLikedMovies().contains(testMovieID));
        assertFalse(moviesInDB.toString().contains("\""+ testMovieID + "\""));
    }

    @Test
    public void registerUser() throws JSONException {
        int result = testSession.getSsapi().registerUser(testSession.getUser()).getContent().getInt(0);
        assertEquals(401, result);
    }

    @Test
    public void userIsValid(){
        assertTrue(testSession.getSsapi().testConnection(testSession.getUser()));
    }




}
