package com.example.fabian.tinf15b4_lsmf.modells;

import com.example.fabian.tinf15b4_lsmf.apis.Ssapi;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

/**
 * Created by s.gerhardt on 10.05.2017.
 */

public class Session {
    private User user;
    private Ssapi ssapi;

    public Session(Ssapi ssapi, User user) {
        this.user = user;
        this.ssapi = ssapi;
    }

    public User getUser() {
        return user;
    }

    public Ssapi getSsapi() {
        return ssapi;
    }



    public void insertLikedMovie(MovieInfo movieinfo){
        user.addLikedMovie(movieinfo.getId());
        ssapi.insertLikedMovie(user, movieinfo);
    }

    public void removeLikedMovie(MovieInfo movieInfo){
        user.removeLikedMovie(movieInfo.getId());
        ssapi.removeLikedMovie(user, movieInfo);
    }
}
