package com.example.eslam.moviaapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieFavAdapterRecycler extends RecyclerView.Adapter<MovieFavAdapterRecycler.MovieRecyclerHolder> {
    private List<Movie> data;

    private final MovieAdapterOnClick movieAdapterOnClick;

    public interface MovieAdapterOnClick {
        void onClick(Movie details);
    }

    public MovieFavAdapterRecycler(MovieAdapterOnClick movieAdapterOnClick, List<Movie> data) {
        this.movieAdapterOnClick = movieAdapterOnClick;
        this.data = data;
    }

    @NonNull
    @Override
    public MovieRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_item_fav, parent, false);
        MovieRecyclerHolder movieRecyclerHolder = new MovieRecyclerHolder(view);
        return movieRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerHolder holder, int position) {
        Movie movie = data.get(position);
        Picasso.with(holder.itemView.getContext()).load(movie.getmMovie_poster()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class MovieRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;

        public MovieRecyclerHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster_fav);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = data.get(adapterPosition);
            movieAdapterOnClick.onClick(movie);
        }
    }
    public void clear() {
        final int size = data.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                data.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }
    public void setMovie(List<Movie> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
