package com.example.popularmoviesapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmoviesapp.adapters.MovieAdapter;
import com.example.popularmoviesapp.database.AppDatabase;
import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.utils.JsonUtils;
import com.example.popularmoviesapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ClickListener, SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<List<Movie>> {

    RecyclerView mRecyclerView;
    ProgressBar mPB;
    TextView mError;
    private final String KEY_FOR_PREFERENCES = "sort";
    private final String DEFAULT_VALUE = "popular";
    private final String BASED_ON_EXTRA = "basedOn";
    private final int MOVIE_LOADER_ID = 25;
    private  static AppDatabase appDatabase;
    List<Movie> favMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_main_activity);
        mPB = findViewById(R.id.pb_loading);
        mError = findViewById(R.id.tv_error_message);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(layoutManager);
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        getSharedPreferenceData();

    }

    private void getSharedPreferenceData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        changeAdapter(sharedPreferences.getString(KEY_FOR_PREFERENCES, DEFAULT_VALUE));
    }

    private List<Movie> makeCallAndParseData(String basedOn) {
        try {
            String data = NetworkUtils.getHttpData(NetworkUtils.getDataUrl(basedOn));
            return JsonUtils.getData(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void showMessage() {
        mError.setVisibility(View.VISIBLE);
    }

    public void hideMessage() {
        mError.setVisibility(View.INVISIBLE);
    }


    public void showProgress() {
        mPB.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_FOR_PREFERENCES)) {
            changeAdapter(sharedPreferences.getString(key, DEFAULT_VALUE));
        }
    }

    public List<Movie> getFavMovies(){
        //MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
        return appDatabase.movieDao().loadAllMovies();
    }

    private void changeAdapter(String value) {
        Bundle bundle = new Bundle();
        bundle.putString(BASED_ON_EXTRA, value);
        LoaderManager loaderManager = getSupportLoaderManager();
        Log.i("Adapter data", "changeAdapter: "+ value);
        Loader<Object> loader = loaderManager.getLoader(MOVIE_LOADER_ID);
        if (null == loader) {
            Log.i("Adapter data", "changeAdapter: init");
            loaderManager.initLoader(MOVIE_LOADER_ID, bundle, this);
        } else {
             loaderManager.restartLoader(MOVIE_LOADER_ID,bundle,this);
        }
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int i, final @Nullable Bundle bundle) {
        return new AsyncTaskLoader<List<Movie>>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.i("Adapter Data", "onStartLoading: " + bundle);
                if (null == bundle)
                    return;
                showProgress();
                forceLoad();
            }

            @Nullable
            @Override
            public List<Movie> loadInBackground() {
                Log.i("Adapter data", "loadInBackground: ");
                String basedOn = bundle.getString(BASED_ON_EXTRA, DEFAULT_VALUE);
                if(basedOn.equals("favourite"))
                    return getFavMovies();
                else
                    return makeCallAndParseData(basedOn);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> movies) {
        if (null == movies || movies.size() <= 0) {
            Log.i("Adapter Data", "onLoadFinished: " + movies);
            showMessage();
            hideProgress();
        }
        else {
            hideMessage();
            hideProgress();
            Log.i("Adapter Data", "onLoadFinished: ");
        }
        mRecyclerView.setAdapter(new MovieAdapter(movies, this));
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        loader = null;
    }

    @Override
    public void clickListener(Movie movie) {
        Toast.makeText(MainActivity.this, "Click on " + movie.getTitle(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, DisplayDetails.class);
        intent.putExtra(DisplayDetails.DATA, movie);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();
        if (selected == R.id.setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
