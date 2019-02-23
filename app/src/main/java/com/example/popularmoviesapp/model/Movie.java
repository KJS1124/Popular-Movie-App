package com.example.popularmoviesapp.model;

import java.io.Serializable;

public class Movie implements Serializable {
    String title;
    int voteCount;
    double voteAvg;
    double popularity;
    String image;
    String lang;
    String overview;
    String releaseDate;
    String backDropPath;

    public Movie(String title, int voteCount, double voteAvg, double popularity, String image, String lang, String overview, String releaseDate, String backDropPath) {
        this.title = title;
        this.voteCount = voteCount;
        this.voteAvg = voteAvg;
        this.popularity = popularity;
        this.image = image;
        this.lang = lang;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backDropPath = backDropPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", voteCount=" + voteCount +
                ", voteAvg=" + voteAvg +
                ", popularity=" + popularity +
                ", image='" + image + '\'' +
                ", lang='" + lang + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", backDropPath='" + backDropPath + '\'' +
                '}';
    }
}
