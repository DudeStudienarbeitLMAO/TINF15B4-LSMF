package com.example.fabian.tinf15b4_lsmf.modells;

import com.example.fabian.tinf15b4_lsmf.apis.Ssapi;
import com.example.fabian.tinf15b4_lsmf.interfaces.MovieLikesChangedListener;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s.gerhardt on 10.05.2017.
 */

public class Session {
    private User user;
    private Ssapi ssapi;
    private List<MovieLikesChangedListener> listeners;

    public Session(Ssapi ssapi, User user) {
        this.user = user;
        this.ssapi = ssapi;
        this.listeners = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public Ssapi getSsapi() {
        return ssapi;
    }


    public void insertLikedMovie(MovieInfo movieinfo) {
        user.addLikedMovie(movieinfo.getId());
        ssapi.insertLikedMovie(user, movieinfo);
        fireMovieLikesChanged();
    }

    public void removeLikedMovie(MovieInfo movieInfo) {
        user.removeLikedMovie(movieInfo.getId());
        ssapi.removeLikedMovie(user, movieInfo);
        fireMovieLikesChanged();
    }

    public void addMovieLikesChangedListener(MovieLikesChangedListener listener) {
        listeners.add(listener);
    }

    private void fireMovieLikesChanged() {
        for (MovieLikesChangedListener listener : listeners) {
            listener.movieLikesChanged();
        }
    }
}
