package com.example.popularmoviesapp.utils;

import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.model.Review;
import com.example.popularmoviesapp.model.Video;

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
    private static String ID = "id";
    private static String VIDEO_KEY = "key";
    private static String VIDEO_NAME = "name";
    private static String REVIEW_AUTHOR = "author";
    private static String REVIEW_DATA = "content";


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
            int id = obj.optInt(ID);
            result.add(new Movie(id, title, 0, voteAverage, popularity, image, language, overview, releaseDate, backdropPath));

        }
        return result;
    }

    public static List<Review> getReviewData(String json) throws JSONException {
        Log.i("Json String", json);
        JSONObject mainObject = new JSONObject(json);
        JSONArray array = mainObject.getJSONArray(ALL_OBJECTS);
        return getReviewModel(array);
    }

    private static List<Review> getReviewModel(JSONArray array) {
        List<Review> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.optJSONObject(i);
            String author = obj.optString(REVIEW_AUTHOR);
            String data = obj.optString(REVIEW_DATA);
            result.add(new Review(author,data));
        }
        return  result;
    }

    public static List<Video> getVideoData(String json)  throws JSONException {
        Log.i("Json String", json);
        JSONObject mainObject = new JSONObject(json);
        JSONArray array = mainObject.getJSONArray(ALL_OBJECTS);
        return getVideoModel(array);
    }

    private static List<Video> getVideoModel(JSONArray array) {
        List<Video> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.optJSONObject(i);
            String name = obj.optString(VIDEO_NAME);
            String key = obj.optString(VIDEO_KEY);
            result.add(new Video(name,key));
        }
        return  result;
    }
}
