package com.example.popularmoviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmoviesapp.DisplayDetails;
import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHoldler> {

    List<Movie> data;
    ClickListener mClickListener;

    public interface ClickListener {
        void clickListener(Movie movie);
    }

    public MovieAdapter(List<Movie> list, ClickListener listener) {
        this.data = list;
        this.mClickListener = listener;
    }


    @NonNull
    @Override
    public MovieViewHoldler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        MovieViewHoldler movieViewHoldler = new MovieViewHoldler(view);
        return movieViewHoldler;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHoldler movieViewHoldler, int i) {
        movieViewHoldler.bind(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MovieViewHoldler extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;

        public MovieViewHoldler(@NonNull View itemView) {

            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_list_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            Picasso.get().load(String.valueOf(NetworkUtils.getImageUrl(movie.getImage().substring(1).toString())))
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickListener.clickListener(data.get(position));
        }
    }
}
