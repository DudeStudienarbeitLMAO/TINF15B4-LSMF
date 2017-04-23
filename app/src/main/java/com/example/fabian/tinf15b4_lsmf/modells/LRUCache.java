package com.example.fabian.tinf15b4_lsmf.modells;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by fabian on 4/22/17.
 */

public class LRUCache {

    private static LRUCache instance;
    private LruCache<String, Bitmap> lru;

    private LRUCache() {
        lru = new LruCache<String, Bitmap>(1024);
    }


    public static LRUCache getInstance() {

        if (instance == null) {

            instance = new LRUCache();
        }

        return instance;

    }

    public LruCache<String, Bitmap> getCache() {
        return lru;
    }

    public void saveBitmapToCache(String key,Bitmap bmp){
       this.getCache().put(key, bmp);
    }


    public Bitmap loadBitmapFromCache(String key){
       return (Bitmap)getCache().get(key);
    }

}
