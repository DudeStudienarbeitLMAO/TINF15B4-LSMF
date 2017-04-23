package com.example.fabian.tinf15b4_lsmf.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fabian.tinf15b4_lsmf.HelperFunctions;
import com.example.fabian.tinf15b4_lsmf.enums.SortOrder;
import com.example.fabian.tinf15b4_lsmf.loadtasks.ImageLoadTask;
import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.modells.LRUCache;
import com.example.fabian.tinf15b4_lsmf.modells.MovieComparator;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fabian on 4/5/17.
 */

public class MovieListAdapter extends ArrayAdapter {

    Context context;
    public ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();
    boolean querying = false;
    MovieComparator movieC;

    HashMap<Integer, String> genreMap;

    public MovieListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortingOrder = prefs.getString("sortingOrder", "");

     if(sortingOrder.equals("1")){
         movieC = new MovieComparator(SortOrder.RATING_DESC);
     }else if(sortingOrder.equals("2")){
         movieC = new MovieComparator(SortOrder.RATING_ASC);
     }else if(sortingOrder.equals("3")){
         movieC = new MovieComparator(SortOrder.NAME_DESC);
     }else if(sortingOrder.equals("4")) {
         movieC = new MovieComparator(SortOrder.NAME_ASC);
     }

        try {
            genreMap = HelperFunctions.mapGenres("de");
        } catch (MovieDbException e) {
            e.printStackTrace();
        }
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
        Collections.sort(movies, movieC);
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

        List<Integer> lg =dataProvider.getGenreIds();
        if(lg!=null && lg.size()>0)
         handler.movieGenre.setText(genreMap.get(lg.get(0)));

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

           Bitmap bmp = LRUCache.getInstance().loadBitmapFromCache(url.substring(1));
            if(bmp!=null){
                handler.movieImage.setImageBitmap(bmp);
            }else{
            new ImageLoadTask(ImageLoadTask.BASE_URL + "w500" + dataProvider.getPosterPath(), handler.movieImage, context).execute();
            }
        }
        return row;
    }
}
