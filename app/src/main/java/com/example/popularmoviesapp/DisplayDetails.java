package com.example.popularmoviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmoviesapp.adapters.ReviewAdapter;
import com.example.popularmoviesapp.adapters.VideoAdapter;
import com.example.popularmoviesapp.database.AppDatabase;
import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.model.Video;
import com.example.popularmoviesapp.utils.JsonUtils;
import com.example.popularmoviesapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> , VideoAdapter.CustomClickListner {

    private static final String MOVIE_ID_KEY = "movie_id";
    public static String MOVIE_KEY = "movie";
    private static int MOVIE_DATA_LOADER = 34;

    ImageView imageView;
    TextView title;
    TextView overview;
    TextView language;
    TextView rating;
    TextView releaseDate;
    AppDatabase mDB;
    Movie movie;
    RecyclerView mVideoRecylerView;
    RecyclerView mReviewRecylerView;

    public static String DATA = "moviedata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);
        imageView = findViewById(R.id.iv_poster);
        title = findViewById(R.id.tv_title);
        overview = findViewById(R.id.tv_overview);
        language = findViewById(R.id.tv_language);
        rating = findViewById(R.id.tv_rating);
        releaseDate = findViewById(R.id.tv_release_date);
        movie = (Movie) getIntent().getSerializableExtra(DATA);
        mVideoRecylerView = findViewById(R.id.video_recycle_view);
        mReviewRecylerView = findViewById(R.id.review_recycle_view);
        mVideoRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mReviewRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mVideoRecylerView.setHasFixedSize(true);
        mReviewRecylerView.setHasFixedSize(true);
        pupulateUI(movie);
        mDB = AppDatabase.getInstance(getApplicationContext());

        LoaderManager manager = getSupportLoaderManager();
        Loader<Object> loader = manager.getLoader(MOVIE_DATA_LOADER);
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID_KEY, movie.getId());
        if (null == loader) {
            Log.d("Loader", "onCreate: ");
            manager.initLoader(MOVIE_DATA_LOADER, bundle,  this).forceLoad();
            Log.d("Loader", "onCreate: ");
        } else {
            manager.restartLoader(MOVIE_DATA_LOADER, bundle, this);
        }
    }

    private void pupulateUI(Movie movie) {
        Picasso.get().load(String.valueOf(NetworkUtils.getImageUrl(movie.getImage().substring(1).toString())))
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        language.setText(movie.getLang());
        rating.setText(movie.getVoteAvg() + "/" + 10);
        releaseDate.setText(movie.getReleaseDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.displayactivitymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favbutton) {
            new InsertMovie().execute(movie);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int i, final @Nullable Bundle bundle) {

        return new AsyncTaskLoader<List<String>>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.d("loader data", "onStartLoading: ");
            }

            @Nullable
            @Override
            public List<String> loadInBackground() {
                Log.d("loader data", "loadInBackground: ");
                if (null == bundle)
                    return null;

                List result = new ArrayList<>();
                try {
                    result.add(NetworkUtils.getHttpData(NetworkUtils.getReviewUrl(bundle.getInt(MOVIE_ID_KEY))));
                    result.add(NetworkUtils.getHttpData(NetworkUtils.getVideoUrl(bundle.getInt(MOVIE_ID_KEY))));
                    Log.d("loader data", "loadInBackground: " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> strings) {
        try {
            mVideoRecylerView.setAdapter(new VideoAdapter(JsonUtils.getVideoData(strings.get(1)),this));
            mReviewRecylerView.setAdapter(new ReviewAdapter(JsonUtils.getReviewData(strings.get(0))));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {
        loader = null;
    }

    @Override
    public void onClick(Video video) {
        Intent intent = new Intent(Intent.ACTION_VIEW,NetworkUtils.getYoutubeVideoUrl(video.getKey()));
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    class InsertMovie extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            Movie movie = movies[0];
            mDB.movieDao().insertMovie(movie);
            return null;
        }
    }
}
