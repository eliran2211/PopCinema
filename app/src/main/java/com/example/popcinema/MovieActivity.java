package com.example.popcinema;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.popcinema.Fragments.MovieFragment;
import com.example.popcinema.Interfaces.Callback_sendMovieItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;


public class MovieActivity extends AppCompatActivity {

    private FrameLayout moviesFrameLayout;
    private DatabaseReference db;
    private Callback_sendMovieItem sendMovieItem;
    private MaterialButton intentWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        db = FirebaseDatabase.getInstance().getReference();

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
        moviesFrameLayout = findViewById(R.id.movies_FRAME_movies);
        intentWishlist = findViewById(R.id.movies_BTN_intentWishlist);
        intentWishlist.setOnClickListener(e -> {
            Intent intent = new Intent(getBaseContext(), WishlistActivity.class);
            startActivity(intent);
        });
    }

    private void initMoviesFragment(ArrayList<MovieItem> movies){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.movies_FRAME_movies, new MovieFragment(movies, sendMovieItem));
        transaction.commitNow();
    }

    private void getMoviesFromDB() {
        db.child("movies").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    ArrayList<MovieItem> movies = new ArrayList<>();
                    for (DataSnapshot movie : task.getResult().getChildren()) {
                        String description = (String) movie.child("description").getValue();
                        String title = (String) movie.child("Title").getValue();
                        String genre = (String) movie.child("Genre").getValue();
                        String actors = (String) movie.child("Actors").getValue();
                        String year = (String) movie.child("Year").getValue();
                        String director = (String) movie.child("Director").getValue();
                        String imageURL = "";
                        for(DataSnapshot image: movie.child("Images").getChildren()){
                            imageURL = image.getValue() + "";
                        }
                        MovieItem movieItem = new MovieItem(title, description, actors, genre, year, director, imageURL);
                        movies.add(movieItem);
                    }
                    initMoviesFragment(movies);
                }
            }
        });
    }
}