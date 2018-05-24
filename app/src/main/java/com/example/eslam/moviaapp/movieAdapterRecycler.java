package com.example.eslam.moviaapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class movieAdapterRecycler extends RecyclerView.Adapter<movieAdapterRecycler.MovieRecyclerHolder> {
    private List<Movie> data;

    private final MovieAdapterOnClick movieAdapterOnClick;

    public interface MovieAdapterOnClick {
        void onClick(Movie details);
    }

    public movieAdapterRecycler(MovieAdapterOnClick movieAdapterOnClick, List<Movie> data) {
        this.movieAdapterOnClick = movieAdapterOnClick;
        this.data = data;
    }

    @NonNull
    @Override
    public MovieRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_item, parent, false);
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
            poster = itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = data.get(adapterPosition);
            movieAdapterOnClick.onClick(movie);
        }
    }

    public void setMovie(List<Movie> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
