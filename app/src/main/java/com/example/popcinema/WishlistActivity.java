package com.example.popcinema;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.popcinema.Fragments.MovieFragment;
import com.example.popcinema.Interfaces.Callback_sendMovieItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    private FrameLayout wishlistFrameLayout;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private String userID;
    private MaterialButton gotoMovies;
    Callback_sendMovieItem sendMovieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        sendMovieItem = new Callback_sendMovieItem() {
            @Override
            public void movieItemClick(MovieItem movieItem) {
                Intent intent = new Intent(getBaseContext(), MovieDetails.class);
                intent.putExtra(MovieDetails.KEY_MOVIE, movieItem);
                startActivity(intent);
            }

            @Override
            public void moviesIntentClick() {
                Intent intent = new Intent(getBaseContext(), MovieActivity.class);
                startActivity(intent);
            }
        };
        getMoviesFromDB();
        addViews();

    }
    private void addViews() {
        wishlistFrameLayout = findViewById(R.id.movies_FRAME_movies);
        gotoMovies = findViewById(R.id.movies_BTN_intentWishlist);
        gotoMovies.setText("Go to all movies");
        gotoMovies.setOnClickListener(e -> {
            Intent intent = new Intent(getBaseContext(), MovieActivity.class);
            startActivity(intent);
        });
    }

    private void initMoviesFragment(ArrayList<MovieItem> movies){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.movies_FRAME_movies, new MovieFragment(movies, sendMovieItem));
        transaction.commitNow();
    }

    private void getMoviesFromDB() {
        db.child("users").child(userID).child("movies").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    ArrayList<MovieItem> movies = new ArrayList<>();
                    for (DataSnapshot movie : task.getResult().getChildren()) {
                        String description = (String) movie.child("description").getValue();
                        String title = (String) movie.child("title").getValue();
                        String genre = (String) movie.child("genre").getValue();
                        String actors = (String) movie.child("actors").getValue();
                        String year = (String) movie.child("year").getValue();
                        String director = (String) movie.child("director").getValue();
                        long rating = (long) movie.child("rating").getValue();
                        String imageURL = (String) movie.child("imageURL").getValue();

                        MovieItem movieItem = new MovieItem(title, description, actors, genre, year, director, imageURL);
                        movieItem.setRating(rating);
                        movies.add(movieItem);
                    }
                    initMoviesFragment(movies);
                }
            }
        });
    }
}