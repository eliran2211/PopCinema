package com.example.popcinema.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.Rating;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.popcinema.Interfaces.Callback_sendMovieItem;
import com.example.popcinema.MovieDetails;
import com.example.popcinema.MovieItem;
import com.example.popcinema.R;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder> {

    private ArrayList<MovieItem> movies;
    private Callback_sendMovieItem sendMovieItemCB;
    Context context;
    public MovieItemAdapter(Context context, ArrayList<MovieItem> movies, Callback_sendMovieItem cb) {
        this.context = context;
        this.movies = movies;
        this.sendMovieItemCB = cb;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        MovieItemViewHolder scoreItemViewHolder = new MovieItemViewHolder(view);
        return scoreItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemAdapter.MovieItemViewHolder holder, int position) {
        MovieItem movie = getItem(position);
        holder.title.setText(movie.getTitle());
        holder.description.setText(movie.getDescription());
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(holder.view).load(movie.getImageURL()).apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.movieImage);
        holder.movieItemWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMovieItemCB.movieItemClick(movie);
            }
        });

        if(movie.getRating() == -1){
            holder.ratingBar.setVisibility(View.GONE);
        }
        holder.ratingBar.setRating(movie.getRating());
    }

    @Override
    public int getItemCount() {
        return this.movies == null ? 0 : movies.size();
    }

    private MovieItem getItem(int position){
        return this.movies.get(position);
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private LinearLayout movieItemWrapper;
        private ImageView movieImage;
        private View view;
        private RatingBar ratingBar;
        private MaterialButton moviesIntent;

        public MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movieItem_LBL_title);
            description = itemView.findViewById(R.id.movieItem_LBL_description);
            movieImage = itemView.findViewById(R.id.movieItem_IMG_movieImg);
            movieItemWrapper = itemView.findViewById(R.id.movieItem_LAYOUT_movieItemWrapper);
            ratingBar = itemView.findViewById(R.id.movieItem_RATING_ratingbar);
            view = itemView;
        }
    }
}
