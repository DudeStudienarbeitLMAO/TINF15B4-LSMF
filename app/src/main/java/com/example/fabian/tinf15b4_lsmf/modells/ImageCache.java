package com.example.fabian.tinf15b4_lsmf.modells;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by fabian on 4/22/17.
 */

public class ImageCache {

    private static ImageCache instance;
    private LruCache<Integer, Bitmap> lru;

    private ImageCache() {
        lru = new LruCache<Integer, Bitmap>(1024);
    }


    public static ImageCache getInstance() {

        if (instance == null) {

            instance = new ImageCache();
        }

        return instance;

    }

    public LruCache<Integer, Bitmap> getCache() {
        return lru;
    }

    public void saveBitmapToCache(Integer key, Bitmap bmp) {
        this.getCache().put(key, bmp);
    }


    public Bitmap loadBitmapFromCache(Integer key) {
        return (Bitmap) getCache().get(key);
    }

}
