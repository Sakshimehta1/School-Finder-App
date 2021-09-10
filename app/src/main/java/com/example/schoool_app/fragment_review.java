package com.example.schoool_app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;

public class fragment_review extends Fragment {

    RecyclerView recyclerView;
    ImageView img_noreview;
    String school_uid;
    FirebaseAuth mauth;
    data_complete data;
    FirebaseUser currentuser;
    RecyclerView.Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference_school;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mauth=FirebaseAuth.getInstance();
        currentuser=mauth.getCurrentUser();
        if(currentuser!=null)
            school_uid=mauth.getCurrentUser().getUid();
        documentReference_school=db.collection("schools").document(school_uid);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDocData();
    }

    private void getDocData() {
        documentReference_school.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    data= documentSnapshot.toObject(data_complete.class);

                    if(documentSnapshot.contains("reviews"))
                        getReviews();
                    else
                    {
                        img_noreview.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }
            }
        });
    }
    private void getReviews() {
            ArrayList<String> reviews = new ArrayList<>();
            reviews.addAll(data.getReviews());
            adapter=new Adapter_review(reviews);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_review, container, false);
        img_noreview=view.findViewById(R.id.img_no_review);
        recyclerView=view.findViewById(R.id.recycler_view);
        return view;
    }
}