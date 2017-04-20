package com.example.fabian.tinf15b4_lsmf.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabian.tinf15b4_lsmf.ImageLoadTask;
import com.example.fabian.tinf15b4_lsmf.R;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by fabian on 4/5/17.
 */

public class MovieListAdapter extends ArrayAdapter {

    Context context;
    public ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();
    boolean querying = false;
    Comparator<MovieInfo> comp;

    public MovieListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        comp = new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo movieInfo, MovieInfo t1) {
                return (int) (t1.getPopularity()-movieInfo.getPopularity())*10;
            }
        };
    }


    static class DataHandler {
        ImageView movieImage;
        TextView movieTitle, movieGenre, movieRating;
    }

    @Override
    public void clear() {
        super.clear();
        movies.clear();
        notifyDataSetChanged();
    }


    @Override
    public void add(Object object) {
        super.add(object);
        movies.add((MovieInfo) object);
        notifyDataSetChanged();
    }

    public boolean isQuerying() {
        return this.querying;
    }

    public void setQuerying(boolean querying) {
        this.querying = querying;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {

        return this.movies.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(movies, comp);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listviewrow, parent, false);
            handler = new DataHandler();
            handler.movieImage = (ImageView) row.findViewById(R.id.movieImage);
            handler.movieGenre = (TextView) row.findViewById(R.id.movieGenre);
            handler.movieTitle = (TextView) row.findViewById(R.id.movieTitle);
            handler.movieRating = (TextView) row.findViewById(R.id.movieRating);
            row.setTag(handler);
        } else {

            handler = (DataHandler) row.getTag();
        }

        MovieInfo dataProvider = (MovieInfo) this.getItem(position);

        handler.movieTitle.setText(dataProvider.getTitle());

        List<Genre> lg = dataProvider.getGenres();
        if(lg!=null && lg.size()>0)
        handler.movieGenre.setText(lg.get(0).getName());

        String shortendRating = "";
        if(dataProvider.getPopularity() > 10) {
            shortendRating = "10";
        }
        else {
            shortendRating = Double.toString(dataProvider.getPopularity()).substring(0, 3);
        }


        handler.movieRating.setText(shortendRating + "/10");

        String url = dataProvider.getPosterPath();



        if ( url != null) {

            ContextWrapper cw = new ContextWrapper(context);
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = null;
            try {
                mypath=new File(directory,java.net.URLEncoder.encode(ImageLoadTask.BASE_URL + "w500" + url, "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(mypath.exists()){

                Bitmap bitmap = BitmapFactory.decodeFile(mypath.getAbsolutePath());
                handler.movieImage.setImageBitmap(bitmap);


            }else{
            new ImageLoadTask(ImageLoadTask.BASE_URL + "w500" + dataProvider.getPosterPath(), handler.movieImage, context).execute();
            }
        }
        return row;
    }
}
