package com.example.popularmoviesapp.utils;

import android.util.Log;

import com.example.popularmoviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String ALL_OBJECTS = "results";
    private static String TITLE = "title";
    private static String POPULARITY = "popularity";
    private static String IMAGE = "poster_path";
    private static String OVERVIEW = "overview";
    private static String RELEASE_DATE = "release_date";
    private static String LANGUAGE = "original_language";
    private static String BACKDROP_PATH = "backdrop_path";
    private static String VOTE_AVG = "vote_average";

    public static List<Movie> getData(String json) throws JSONException {
        Log.i("Json String", json);
        JSONObject mainObject = new JSONObject(json);
        JSONArray array = mainObject.getJSONArray(ALL_OBJECTS);
        return getModels(array);
    }

    private static List<Movie> getModels(JSONArray array) {
        List<Movie> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.optJSONObject(i);
            String title = obj.optString(TITLE);
            double popularity = obj.optDouble(POPULARITY);
            String image = obj.optString(IMAGE);
            String overview = obj.optString(OVERVIEW);
            String releaseDate = obj.optString(RELEASE_DATE);
            String language = obj.optString(LANGUAGE);
            String backdropPath = obj.optString(BACKDROP_PATH);
            double voteAverage = obj.optDouble(VOTE_AVG);
            result.add(new Movie(title, 0, voteAverage, popularity, image, language, overview, releaseDate, backdropPath));

        }
        return result;
    }
}
