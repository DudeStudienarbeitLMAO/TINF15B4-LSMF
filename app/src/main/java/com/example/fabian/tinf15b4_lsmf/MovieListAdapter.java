package com.example.fabian.tinf15b4_lsmf;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabian on 4/5/17.
 */

public class MovieListAdapter extends ArrayAdapter {

    ArrayList<Movie> movies = new ArrayList<Movie>();
    public MovieListAdapter(Context context, int resource) {
        super(context, resource);
    }


    static class DataHandler{
        ImageView movieImage;
        TextView movieTitle, movieGenre, movieRating;
    }


    @Override
    public void add(Object object) {
        super.add(object);
        movies.add((Movie) object);
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

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DataHandler handler;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listviewrow, parent, false);
            handler = new DataHandler();
            handler.movieImage = (ImageView) row.findViewById(R.id.movieImage);
            handler.movieGenre = (TextView) row.findViewById(R.id.movieGenre);
            handler.movieTitle = (TextView) row.findViewById(R.id.movieTitle);
            handler.movieRating = (TextView) row.findViewById(R.id.movieRating);
            row.setTag(handler);
        }else{

            handler = (DataHandler) row.getTag();
        }

        Movie dataProvider = (Movie) this.getItem(position);

        handler.movieTitle.setText(dataProvider.getTitle());
        handler.movieGenre.setText(dataProvider.getGenre());
        handler.movieRating.setText(dataProvider.getRating() + "/10");

        return row;
    }
}
