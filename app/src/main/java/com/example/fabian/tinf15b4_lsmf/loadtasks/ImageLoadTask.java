package com.example.fabian.tinf15b4_lsmf.loadtasks;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.fabian.tinf15b4_lsmf.modells.LRUCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fabian on 4/5/17.
 */

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private ImageView imageView;
    private Context AppContext;
    public static final String BASE_URL =  "https://image.tmdb.org/t/p/";

    public ImageLoadTask(String url, ImageView imageView, Context con) {
        this.url = url;
        this.imageView = imageView;
        this.AppContext = con;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            String[] urlParts = url.split("/");
            LRUCache.getInstance().saveBitmapToCache(urlParts[urlParts.length-1], myBitmap);

            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }
}
