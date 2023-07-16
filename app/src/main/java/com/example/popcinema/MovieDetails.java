package com.example.popcinema;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class MovieDetails extends AppCompatActivity {

    public static String KEY_MOVIE = "KEY_MOVIE";

    private TextView title;
    private ImageView image;
    private TextView description;
    private TextView actors;
    private TextView genre;
    private TextView year;
    private TextView director;
    private Button addWishlist;
    private RatingBar rating;
    private MaterialButton moviesIntent;

    private FirebaseAuth auth;
    private DatabaseReference DB;

    private MovieItem movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        auth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        movie = (MovieItem) intent.getSerializableExtra(KEY_MOVIE);

        addView();
        setContent();
    }

    private void addView() {
        title = findViewById(R.id.details_LBL_title);
        image = findViewById(R.id.details_IMG_image);
        description = findViewById(R.id.details_LBL_description);
        actors = findViewById(R.id.details_LBL_actors);
        genre = findViewById(R.id.details_LBL_genre);
        year = findViewById(R.id.details_LBL_year);
        director = findViewById(R.id.details_LBL_director);
        addWishlist = findViewById(R.id.details_LBL_addWishlist);
        rating = findViewById(R.id.details_RATING_ratingbar);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                movie.setRating((int)rating);
            }
        });

        addWishlist.setOnClickListener(e -> {
            DB.child("users").child(auth.getCurrentUser().getUid()).child("movies").child(movie.getTitle()).setValue(movie);
            Intent intent = new Intent(getBaseContext(), MovieActivity.class);
            startActivity(intent);
        });

        moviesIntent = findViewById(R.id.details_BTN_gotoMovies);
        moviesIntent.setOnClickListener(e -> {
            Intent intent = new Intent(getBaseContext(), MovieActivity.class);
            startActivity(intent);
        });
    }

    private void setContent() {
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        actors.setText(movie.getActors());
        genre.setText(movie.getGenre());
        year.setText(movie.getYear());
        director.setText(movie.getDirector());
        Glide.with(this).load(movie.getImageURL()).into(image);
    }
}