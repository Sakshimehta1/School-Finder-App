package com.example.schoool_app;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_review extends RecyclerView.Adapter<Adapter_review.MyViewHolder> {

    private ArrayList<String> mreviews;
    public Adapter_review(ArrayList<String> reviews) {
        mreviews= reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.reviewtv.setText(mreviews.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mreviews.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView reviewtv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewtv=itemView.findViewById(R.id.review_list);
        }
    }
}
