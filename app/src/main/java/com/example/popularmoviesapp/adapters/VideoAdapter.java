package com.example.popularmoviesapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.model.Video;
import com.example.popularmoviesapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    List<Video> videos;

    public VideoAdapter(List<Video> videos){
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        videoViewHolder.bind(videos.get(i));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_video_list_item);
        }

        public void bind(Video video){
            Picasso.get().load(NetworkUtils.getYoutubeImageUrl(video.getKey()))
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(mImageView);
        }
    }
}
