package com.example.fabian.tinf15b4_lsmf.modells;

import android.util.LruCache;

import com.omertron.themoviedbapi.model.movie.MovieInfo;

/**
 * Created by s.gerhardt on 10.05.2017.
 */

public class LikedMovieCache {
    private static LikedMovieCache instance;
    private LruCache<Integer, MovieInfo> lru;

    private LikedMovieCache() {
        lru = new LruCache<Integer, MovieInfo>(1024);
    }


    public static LikedMovieCache getInstance() {

        if (instance == null) {

            instance = new LikedMovieCache();
        }

        return instance;

    }

    public LruCache<Integer, MovieInfo> getCache() {
        return lru;
    }

    public void saveMovieToCache(Integer key, MovieInfo bmp) {
        this.getCache().put(key, bmp);
    }


    public MovieInfo loadMovieFromCache(Integer key) {
        return (MovieInfo) getCache().get(key);
    }
}
