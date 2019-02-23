package com.example.popularmoviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.popularmoviesapp.adapters.MovieAdapter;
import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.utils.JsonUtils;
import com.example.popularmoviesapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_main_activity);
        new MovieQueryTask().execute("popular");

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


    class MovieQueryTask extends AsyncTask<String, Void, List<Movie>> implements MovieAdapter.ClickListener {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String basedOn = strings[0];
            return makeCallAndParseData(basedOn);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            Log.d("Got data", movies.toString());
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(new MovieAdapter(movies, this));
        }

        @Override
        public void clickListener(Movie movie) {
            Toast.makeText(MainActivity.this, "Click on " + movie.getTitle(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, DisplayDetails.class);
            intent.putExtra(DisplayDetails.DATA, movie);
            startActivity(intent);

        }
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
        switch (selected) {
            case R.id.top:
                new MovieQueryTask().execute("top");
                break;
            case R.id.popular:
                new MovieQueryTask().execute("popular");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
