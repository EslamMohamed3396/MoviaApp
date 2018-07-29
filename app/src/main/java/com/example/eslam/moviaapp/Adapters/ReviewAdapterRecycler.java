package com.example.eslam.moviaapp.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eslam.moviaapp.R;
import com.example.eslam.moviaapp.Models.Review;

import java.util.List;

public class ReviewAdapterRecycler extends RecyclerView.Adapter<ReviewAdapterRecycler.ReviewRecyclerHolder> {
    private List<Review> data;
    private Context context;


    public ReviewAdapterRecycler(Context context, List<Review> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ReviewRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review, parent, false);
        ReviewRecyclerHolder reviewRecyclerHolder = new ReviewRecyclerHolder(view);
        return reviewRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerHolder holder, int position) {
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

    public class ReviewRecyclerHolder extends RecyclerView.ViewHolder {
        TextView tv_author, tv_content;

        public ReviewRecyclerHolder(View itemView) {
            super(itemView);
            tv_author = itemView.findViewById(R.id.author);
            tv_content = itemView.findViewById(R.id.content);
        }


    }

    public void setMovie(List<Review> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
