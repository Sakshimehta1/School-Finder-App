package com.example.schoool_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecViewSchoolPhotos extends RecyclerView.Adapter<RecViewSchoolPhotos.ViewHolder> {

    List<String> schlphotos;
    private Context context;

    public RecViewSchoolPhotos(List<String> cschlphotos,Context context)
    {
        this.schlphotos=cschlphotos;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.rec_view_school_phoitos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.images.setImageResource(R.drawable.ic_school_logo);
        Glide.with(this.context)
                .load(schlphotos.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.images);
        Picasso.get().load(schlphotos.get(position)).fit().centerCrop().into(holder.images);
//        Picasso.get().load(schlphotos.get(position)).into(holder.images);
//        holder.images.setImageBitmap(schlphotos.get(position));
    }

    @Override
    public int getItemCount() {
        return schlphotos.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.imgview);
        }
    }
}
