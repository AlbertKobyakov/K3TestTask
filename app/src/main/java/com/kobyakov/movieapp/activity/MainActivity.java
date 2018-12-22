package com.kobyakov.movieapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kobyakov.movieapp.R;
import com.kobyakov.movieapp.RecyclerTouchListener;
import com.kobyakov.movieapp.adapterRecyclerView.MainActivityAdapter;
import com.kobyakov.movieapp.model.Movie;
import com.kobyakov.movieapp.viewmodel.MainActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();
    final int LAYOUT = R.layout.activity_main;

    MainActivityAdapter mAdapter;
    MainActivityViewModel viewModel;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView noInternetText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);
        initRecyclerView();

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.getMovieList().observe(this, movieList -> {
            if (movieList != null && movieList.size() > 0) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                mAdapter.setData(movieList);
            }
        });

        viewModel.getStatusCode().observe(this, statusCode -> {
            if (statusCode != null) {
                progressBar.setVisibility(View.GONE);
                blockError.setVisibility(View.VISIBLE);
                noInternetText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new MainActivityAdapter(this, Glide.with(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.count_movies)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, (view, position) -> {
            if (mAdapter.getMovies() != null && mAdapter.getMovies().size() >= position) {
                Movie movie = mAdapter.getMovies().get(position);
                goToMovieDetailActivity(movie);
            }
        }));
    }

    public void goToMovieDetailActivity(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movieTitle", movie.getTitle());
        intent.putExtra("movieDesc", movie.getOverview());
        startActivity(intent);
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.repeatRequest();

        blockError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}