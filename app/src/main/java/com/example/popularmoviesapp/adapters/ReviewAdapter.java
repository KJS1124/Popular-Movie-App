package com.example.popularmoviesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.model.Review;

import org.w3c.dom.Text;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    List<Review> review;
    private static final String AUTHOR = "Author";
    private static final String COMMENT = "Comment";

    public ReviewAdapter(List<Review> review) {
        this.review = review;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.review_list_item, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        reviewViewHolder.bind(review.get(i));
    }

    @Override
    public int getItemCount() {
        return review.size();
    }


    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView review;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.review_author);
            review = itemView.findViewById(R.id.review_comment);
        }

        public void bind(Review reviewP) {
            author.setText(AUTHOR + ": " + reviewP.getAuthor());
            review.setText(COMMENT + "\n" + reviewP.getReview());
        }
    }
}
