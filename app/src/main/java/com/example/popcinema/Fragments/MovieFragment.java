package com.example.popcinema.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcinema.Adapters.MovieItemAdapter;
import com.example.popcinema.Interfaces.Callback_sendMovieItem;
import com.example.popcinema.MovieItem;
import com.example.popcinema.R;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    private ArrayList<MovieItem> movies;
    private RecyclerView moviesListRecycle;
    private Callback_sendMovieItem sendMovieItem;

    public MovieFragment(ArrayList<MovieItem> movies, Callback_sendMovieItem sendMovieItem) {
        this.movies = movies;
        this.sendMovieItem = sendMovieItem;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        moviesListRecycle = view.findViewById(R.id.movieFragment_RECYCLE_list);
        MovieItemAdapter movieAdapter = new MovieItemAdapter(getActivity(), movies, sendMovieItem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        moviesListRecycle.setLayoutManager(linearLayoutManager);
        moviesListRecycle.setAdapter(movieAdapter);
        return view;
    }
}