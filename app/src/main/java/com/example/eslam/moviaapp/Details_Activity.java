package com.example.eslam.moviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__movie);
        TextView titleText = findViewById(R.id.title);
        ImageView posterImage = findViewById(R.id.im_poster);
        TextView overText = findViewById(R.id.over);
        TextView rateText = findViewById(R.id.rate);
        TextView dateText = findViewById(R.id.date);
        Intent intent = getIntent();
        titleText.setText(intent.getStringExtra("title"));
        String s = intent.getStringExtra("poster");
        Picasso.with(this).load(s).into(posterImage);
        overText.setText(intent.getStringExtra("over"));
        rateText.setText(intent.getStringExtra("rate"));
        dateText.setText(intent.getStringExtra("date"));
    }
}