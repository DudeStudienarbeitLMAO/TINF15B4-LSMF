package com.example.fabian.tinf15b4_lsmf.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabian.tinf15b4_lsmf.HelperFunctions;
import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.enums.SortOrder;
import com.example.fabian.tinf15b4_lsmf.loadtasks.ImageLoadTask;
import com.example.fabian.tinf15b4_lsmf.modells.ImageCache;
import com.example.fabian.tinf15b4_lsmf.modells.MovieComparator;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by fabian on 4/5/17.
 */

public class MovieListAdapter extends ArrayAdapter  {

    private Context context;
    private ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();
    private boolean querying = false;
    private MovieComparator movieC;

    private HashMap<Integer, String> genreMap;
    SharedPreferences prefs;


    public void updateSorting(){
        String sortingOrder = prefs.getString("sortingOrder", "DEFAULT");

        if ("1".equals(sortingOrder)) {
            movieC = new MovieComparator(SortOrder.RATING_DESC);
        } else if ("2".equals(sortingOrder)) {
            movieC = new MovieComparator(SortOrder.RATING_ASC);
        } else if ("3".equals(sortingOrder)) {
            movieC = new MovieComparator(SortOrder.NAME_DESC);
        } else if ("4".equals(sortingOrder)) {
            movieC = new MovieComparator(SortOrder.NAME_ASC);
        }else{
            movieC = new MovieComparator(SortOrder.RATING_DESC);
        }

    }

    public MovieListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;

        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        updateSorting();

        try {

            genreMap = HelperFunctions.getInstance().getGenreMap("de");
        } catch (MovieDbException e) {
            e.printStackTrace();
        }
    }



    static class DataHandler {
        private ImageView movieImage;
        private TextView movieTitle, movieGenre, movieRating;
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

        super.notifyDataSetChanged();
    }

    public void sort(){
        updateSorting();
        //Collections.sort(movies, movieC);
        movies.sort(movieC);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.movie_list_view_row, parent, false);
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

        List<Integer> genreIDs = dataProvider.getGenreIds();
        List<Genre> genres = dataProvider.getGenres();

        if (genreIDs != null && genreIDs.size() > 0) {
            handler.movieGenre.setText(genreMap.get(genreIDs.get(0)));
        }
        else if(genres != null && genres.size() > 0) {
            handler.movieGenre.setText(genres.get(0).getName());
        }

        String shortendRating = "";

        if (dataProvider.getVoteAverage() >= 10) {
            shortendRating = "10";
        } else {
            shortendRating = Float.toString(dataProvider.getVoteAverage()).substring(0, 3);
        }


        handler.movieRating.setText(shortendRating + "/10");

        String url = dataProvider.getPosterPath();


        if (url != null) {

            Bitmap bmp = ImageCache.getInstance().loadBitmapFromCache(dataProvider.getId());
            if (bmp != null) {
                handler.movieImage.setImageBitmap(bmp);
            } else {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                Boolean loadImgs = prefs.getBoolean("loadImages", false );
                if(loadImgs) {
                    new ImageLoadTask(ImageLoadTask.BASE_URL + "w500" + dataProvider.getPosterPath(), handler.movieImage, dataProvider.getId()).execute();
                }
            }
        }
        return row;
    }
}
