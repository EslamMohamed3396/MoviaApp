package com.example.eslam.moviaapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.eslam.moviaapp.R;

import com.example.eslam.moviaapp.Models.Trailer;

import java.util.List;


public class TrailerAdapterRecycler extends RecyclerView.Adapter<TrailerAdapterRecycler.TrialerRecyclerHolder> {
    private List<Trailer> data;
    private Context context;


    public TrailerAdapterRecycler(Context context, List<Trailer> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TrialerRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailr_item, parent, false);
        TrialerRecyclerHolder movieRecyclerHolder = new TrialerRecyclerHolder(view);
        return movieRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrialerRecyclerHolder holder, int position) {
        Trailer trailer = data.get(position);
        String frameVideo = "<iframe src=\"https://www.youtube.com/embed/" + trailer.getmTrailer() + "\" frameborder=\"0\" allowfullscreen></iframe>";
        holder.webView.loadData(frameVideo, "text/html", "utf-8");
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class TrialerRecyclerHolder extends RecyclerView.ViewHolder {
        WebView webView;

        public TrialerRecyclerHolder(View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.web);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
        }


    }

    public void setMovie(List<Trailer> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
