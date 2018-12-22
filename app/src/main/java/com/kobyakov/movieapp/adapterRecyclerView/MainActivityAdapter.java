package com.kobyakov.movieapp.adapterRecyclerView;

import android.content.Context;
import android.icu.util.LocaleData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.kobyakov.movieapp.R;
import com.kobyakov.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MyViewHolder> {

    private static final int LAYOUT = R.layout.movie_list_row;
    private final String TAG = getClass().getSimpleName();

    private List<Movie> movies;
    private Context context;
    private RequestManager glide;
    private String path = "https://image.tmdb.org/t/p/w185_and_h278_bestv2";

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_rating)
        TextView movieRating;
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.title_and_vote)
        LinearLayout titleVoteBlock;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public MainActivityAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    public void setData(List<Movie> resultsList) {
        this.movies = new ArrayList<>(resultsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MainActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapter.MyViewHolder holder, int position) {
        if (movies != null) {
            Movie movie = movies.get(position);
            Log.d(TAG, movie.toString());

            if (movie.getTitle() != null && movie.getTitle().trim().length() > 0) {
                holder.movieTitle.setText(movie.getTitle());
            } else {
                holder.movieTitle.setText(context.getResources().getString(R.string.unknown));
            }

            if (movie.getVoteAverage() != 0 && movie.getVoteCount() != 0) {
                holder.movieRating.setText(context.getString(R.string.vote_count_and_vote_average, Double.toString(movie.getVoteAverage()), movie.getVoteCount()));
            } else {
                holder.movieRating.setText(context.getResources().getString(R.string.unknown));
            }

            Log.d(TAG, position + " ");

            if(position%2 != 0){
                holder.titleVoteBlock.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                holder.titleVoteBlock.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

            glide.load(path + movie.getPosterPath())
                    .error(glide.load(R.drawable.help))
                    .into(holder.moviePoster);
        }
    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.size();
        } else {
            return 0;
        }
    }
}