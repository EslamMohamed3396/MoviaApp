package com.example.eslam.moviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details_Movie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__movie);
        TextView title = findViewById(R.id.title);
        ImageView poster = findViewById(R.id.im_poster);
        TextView over = findViewById(R.id.over);
        TextView rate = findViewById(R.id.rate);
        TextView date = findViewById(R.id.date);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        String s = intent.getStringExtra("poster");
        Picasso.with(this).load(s).into(poster);
        over.setText(intent.getStringExtra("over"));
        rate.setText(intent.getStringExtra("rate"));
        date.setText(intent.getStringExtra("date"));
    }
}
