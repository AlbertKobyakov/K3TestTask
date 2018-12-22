package com.kobyakov.movieapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.kobyakov.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    private final static int LAYOUT = R.layout.activity_movie_detail;

    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.movie_desc)
    TextView movieDesc;

    private String title;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title = getIntent().getStringExtra("movieTitle");
        desc = getIntent().getStringExtra("movieDesc");

        movieTitle.setText(title);
        movieDesc.setText(desc);
    }
}