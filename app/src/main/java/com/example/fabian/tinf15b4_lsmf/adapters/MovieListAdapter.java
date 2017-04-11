package com.example.fabian.tinf15b4_lsmf.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabian.tinf15b4_lsmf.ImageLoadTask;
import com.example.fabian.tinf15b4_lsmf.modells.Movie;
import com.example.fabian.tinf15b4_lsmf.R;

import java.util.ArrayList;

/**
 * Created by fabian on 4/5/17.
 */

public class MovieListAdapter extends ArrayAdapter {

    Context context1;
    ArrayList<Movie> movies = new ArrayList<Movie>();
    boolean querying = false;

    public MovieListAdapter(Context context, int resource) {
        super(context, resource);
        context1 = context;
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
        movies.add((Movie) object);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        Movie dataProvider = (Movie) this.getItem(position);

        handler.movieTitle.setText(dataProvider.getTitle());
        handler.movieGenre.setText(dataProvider.getGenre());

        String shortendRating = "";
        if(dataProvider.getRating() > 10) {
            shortendRating = "10";
        }
        else {
            shortendRating = Double.toString(dataProvider.getRating()).substring(0, 3);
        }


        handler.movieRating.setText(shortendRating + "/10");
        if (dataProvider.getPosterURL() != null)
            new ImageLoadTask(dataProvider.getPosterURL(), handler.movieImage).execute();
        return row;
    }
}
