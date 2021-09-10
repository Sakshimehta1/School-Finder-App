package com.example.schoool_app;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterListRec extends RecyclerView.Adapter<AdapterListRec.MyViewholder>{
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class MyViewholder extends RecyclerView.ViewHolder{

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
