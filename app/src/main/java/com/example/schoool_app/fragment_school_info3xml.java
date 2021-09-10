package com.example.schoool_app;

import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class fragment_school_info3xml extends Fragment {

    ImageView fb , insta , ytb , linkin;
    TextInputLayout fbed, instaed , ytbed, linkined,fees;
    AutoCompleteTextView doc,fac;
    String infac,indoc,infees;
    MaterialButton save;
    ChipGroup chipfac,chipdoc;
    ProgressBar progressBar;
    List<String> chips_doc;
    List<String> chips_fac;
    String infb,ininsta,inytb,inlinkin;
    String uid;

    public static final String SHARED_PREF="sharedPrefs";
    public static final String FLAG="flag";

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_info3xml, container, false);
        fb=view.findViewById(R.id.fbimage);
        insta=view.findViewById(R.id.instaimg);
        ytb=view.findViewById(R.id.youtubeimg);
        linkin=view.findViewById(R.id.linkimage);
        fbed=view.findViewById(R.id.fbedit);
        instaed=view.findViewById(R.id.instaedit);
        ytbed=view.findViewById(R.id.youtbedit);
        linkined=view.findViewById(R.id.linkedit);
        save=view.findViewById(R.id.save);
        fees=view.findViewById(R.id.fees);
        progressBar=view.findViewById(R.id.prog3);
        chipdoc=view.findViewById(R.id.chip_group_doc);
        chipfac=view.findViewById(R.id.chip_group_fac);
        fac=view.findViewById(R.id.fac);
        doc=view.findViewById(R.id.documnts);
        chips_fac=new ArrayList<>();
        chips_doc=new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid=currentUser.getUid();
        documentReference=db.collection("schools").document(uid);

        fac.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.facilities)));
        doc.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.documents)));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook();
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagram();
            }
        });
        ytb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtube();
            }
        });
        linkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedin();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields())
                {
                    progressBar.setVisibility(View.VISIBLE);
                    saveDatatoFirebase();
                }
            }
        });
        fac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String input =fac.getText().toString();
                addfacchip(input);
            }
        });
        doc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String input =doc.getText().toString();
                add_docchip(input);
            }
        });

    }

    private void add_docchip(final String input) {
        final Chip chip=new Chip(getActivity());
        chip.setText(input);
        chips_doc.add(input); //list of chips
        chip.setCloseIconVisible(true);
        chipdoc.addView(chip);
         chipdoc.setVisibility(View.VISIBLE);
         chip.setOnCloseIconClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 chipdoc.removeView(v);
                 chips_doc.remove(input);
             }
         });
    }

    private void addfacchip(final String input) {
        final Chip chip=new Chip(getActivity());
        chip.setText(input);
        chips_fac.add(input);
        chip.setCloseIconVisible(true);
        chipfac.addView(chip);
        chipfac.setVisibility(View.VISIBLE);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipfac.removeView(v);
                chips_fac.remove(input);
            }
        });
    }


    private void saveDatatoFirebase() {
        getdata();
        data_school_info3 data = new data_school_info3(infees,chips_doc,chips_fac,infb,ininsta,inytb,inlinkin);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(data, Map.class);
        documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(FLAG,4);
                editor.apply();

                Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),SchoolScreen.class));
            }
        });
    }

    private void getdata() {
        inytb=ytbed.getEditText().getText().toString();
        infb=fbed.getEditText().getText().toString();
        ininsta=instaed.getEditText().getText().toString();
        inlinkin=linkined.getEditText().getText().toString();
        infees=fees.getEditText().getText().toString();

    }

    private void instagram() {
        instaed.setVisibility(View.VISIBLE);
        ytbed.setVisibility(View.GONE);
        linkined.setVisibility(View.GONE);
        fbed.setVisibility(View.GONE);
    }

    private void youtube() {
        ytbed.setVisibility(View.VISIBLE);
        instaed.setVisibility(View.GONE);
        linkined.setVisibility(View.GONE);
        fbed.setVisibility(View.GONE);
    }

    private void linkedin() {
        linkined.setVisibility(View.VISIBLE);
        instaed.setVisibility(View.GONE);
        ytbed.setVisibility(View.GONE);
        fbed.setVisibility(View.GONE);
    }

    private void facebook() {
        fbed.setVisibility(View.VISIBLE);
        instaed.setVisibility(View.GONE);
        linkined.setVisibility(View.GONE);
        ytbed.setVisibility(View.GONE);
    }

    private boolean validateFields() {
        infees=fees.getEditText().getText().toString();
        infac=fac.getText().toString();
        indoc=doc.getText().toString();

        if(infees.isEmpty())
        {
            fees.setError("Field can't be empty");
            return false;
        }
        else
            fees.setError(null);
        if(indoc.isEmpty())
        {
            doc.setError("Field can't be empty");
            return false;
        }
        else
            doc.setError(null);
        if(infac.isEmpty())
        {
            fac.setError("Field can't be empty");
            return false;
        }
        else
            fac.setError(null);

        return true;
    }

}