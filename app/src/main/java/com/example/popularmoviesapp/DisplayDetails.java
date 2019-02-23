package com.example.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DisplayDetails extends AppCompatActivity {

    ImageView imageView;
    TextView title;
    TextView overview;
    TextView language;
    TextView rating;
    TextView releaseDate;


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
        Movie movie = (Movie) getIntent().getSerializableExtra(DATA);
        pupulateUI(movie);
    }

    private void pupulateUI(Movie movie) {
        Picasso.get().load(String.valueOf(NetworkUtils.getImageUrl(movie.getImage().substring(1).toString())))
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        language.setText(movie.getLang());
        rating.setText(movie.getVoteAvg()+"/"+10);
        releaseDate.setText(movie.getReleaseDate());

    }
}
