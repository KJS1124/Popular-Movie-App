package com.example.popularmoviesapp.utils;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    public static final String API_KEY = "01fc236f22edef7f8ecfb2fc4207c898";
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/popular";
    public static final String KEY_PARAM = "api_key";
    public static final String SIZE = "w185";
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";
    public  static final String SORT_PARAM = "sort_by";
    public  static final String SORT_BY_POPULARIRTY = "popularity.desc";
    public  static final String SORT_BY_RATING = "vote_average.desc";



    public static URL getImageUrl(String image) {
        Uri uri = Uri.parse(BASE_IMAGE_URL)
                .buildUpon()
                .appendPath(SIZE)
                .appendPath(image)
                .build();
        Log.i("uri is ", uri.toString());
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("Created Path", url.toString());
        return url;
    }

    public static String getHttpData(URL url) throws IOException {
        String result = "";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext())
                result = scanner.next();
        }catch (IOException exception){
            exception.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        Log.d("Getting data", result);
        return result;
    }

    public static URL getDataUrl(String basedOn) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter(KEY_PARAM,API_KEY);
        switch (basedOn){
            case "popular": uriBuilder.appendQueryParameter(SORT_PARAM,SORT_BY_POPULARIRTY); break;
            case "top": uriBuilder.appendQueryParameter(SORT_PARAM,SORT_BY_RATING); break;
        }
        Uri uri = uriBuilder.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("Created Path", url.toString());
        return url;
    }
}
