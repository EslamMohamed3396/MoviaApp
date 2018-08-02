package com.example.eslam.moviaapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.R;

import java.util.List;

public class ReviewFavAdabterRecycler extends RecyclerView.Adapter<ReviewFavAdabterRecycler.ReviewFavRecyclerHolder> {
    private List<Review> data;
    private Context context;

    public ReviewFavAdabterRecycler(Context context, List<Review> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ReviewFavRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review_fav, parent, false);
        ReviewFavRecyclerHolder reviewRecyclerHolder = new ReviewFavRecyclerHolder(view);
        return reviewRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewFavRecyclerHolder holder, int position) {
        Review review = data.get(position);
        if (review.hasContent()) {
            holder.tv_author.setText(review.getmAuthor());
            holder.tv_content.setText(review.getmContent());
        } else {
            holder.tv_content.setText(holder.itemView.getResources().getString(R.string.no_review));
        }
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class ReviewFavRecyclerHolder extends RecyclerView.ViewHolder {
        TextView tv_author, tv_content;

        public ReviewFavRecyclerHolder(View itemView) {
            super(itemView);
            tv_author = itemView.findViewById(R.id.author_fav);
            tv_content = itemView.findViewById(R.id.content_fav);
        }


    }

    public void setMovie(List<Review> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
