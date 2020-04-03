package com.hillary.moviestage1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.hillary.moviestage1.*;
import java.net.URL;
import java.util.ArrayList;

import Data.MoviesAdapter;
import Data.*;
import Util.MoviesJson;
import Util.Network;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler {
    private ProgressBar mNetworkLoadProgressBar;
    private TextView mErrorTextView;
    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mMovieAdapter;
    Button mRefreshButton;
    private static final int SPAN_COUNT = 2;
    private static Network.preferredSortType currentSortType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetworkLoadProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mErrorTextView = (TextView) findViewById(R.id.error_text);
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.movies_list);
        mRefreshButton = (Button) findViewById(R.id.refresh);
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MoviesAdapter(this);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);
        currentSortType = Network.preferredSortType.POPULARITY;
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMovieData();
            }
        });
        fetchMovieData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:
                currentSortType = Network.preferredSortType.POPULARITY;
                fetchMovieData();
                if (mErrorTextView.getVisibility() == View.VISIBLE) {
                    Toast.makeText(getApplicationContext(),"No data", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.option_2:
                currentSortType = Network.preferredSortType.RATING;
                fetchMovieData();
                if (mErrorTextView.getVisibility() == View.VISIBLE) {
                    Toast.makeText(getApplicationContext(),"No data", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(MoviesData movie) {
        Intent intent = new Intent(this, details_activity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }

    private void fetchMovieData() {
        showMoviesList();
        new FetchMoviesTask().execute(currentSortType);
    }

    private void showMoviesList() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRefreshButton.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideMoviesList() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mRefreshButton.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
    }

    private class FetchMoviesTask extends AsyncTask<Network.preferredSortType, Void, ArrayList<MoviesData>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mNetworkLoadProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MoviesData> doInBackground(Network.preferredSortType... params) {
            ArrayList<MoviesData> movieArrayList = null;
            if (params[0] == null) {
                return null;
            }
            try {
                URL url = Network.buildURL(params[0]);
                String jsonData = Network.getJsonFromHttpUrl(url);
                movieArrayList = MoviesJson.getMovieArrayListFromJson(jsonData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return movieArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<MoviesData> movieArrayList) {
            mNetworkLoadProgressBar.setVisibility(View.INVISIBLE);
            if (movieArrayList != null) {
                showMoviesList();
                mMovieAdapter.setMovieArrayList(movieArrayList);
            } else {
                hideMoviesList();
            }
        }
    }
}