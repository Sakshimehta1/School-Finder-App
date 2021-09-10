package com.example.schoool_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Fragment_school_info1 extends Fragment {

    TextInputLayout name,address,city,state,landmark,website,contact;
    MaterialButton save;
    Fragment info2;
    ProgressBar progressBar;
    String inname,inaddress,incity,instate,inland,inweb,incontact;
    String uid;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseUser currentUser;
    DocumentReference documentReference;
    public static final String SHARED_PREF="sharedPrefs";
    public static final String FLAG="flag";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_info1, container, false);
        save=view.findViewById(R.id.save1);
        progressBar=view.findViewById(R.id.progress_circular);

        name=view.findViewById(R.id.school_name);
        address=view.findViewById(R.id.school_address);
        city=view.findViewById(R.id.city);
        state=view.findViewById(R.id.state);
        landmark=view.findViewById(R.id.landmark);
        website=view.findViewById(R.id.website);
        contact=view.findViewById(R.id.contactNo);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid=currentUser.getUid();
        documentReference=db.collection("schools").document(uid);
        info2=new fragment_school_info2xml();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    private boolean validateFields() {
        inname=name.getEditText().getText().toString();
        inaddress=address.getEditText().getText().toString();
        incity=city.getEditText().getText().toString();
        instate=state.getEditText().getText().toString();
        inland=landmark.getEditText().getText().toString();
        inweb=website.getEditText().getText().toString();
        incontact=contact.getEditText().getText().toString();

        if(inname.isEmpty())
        {
            name.setError("Field can't be empty");
            return false;
        }
        else
            name.setError(null);
        if(inaddress.isEmpty())
        {
            address.setError("Field can't be empty");
            return false;
        }
        else
            address.setError(null);
        if(incity.isEmpty())
        {
            city.setError("Field can't be empty");
            return false;
        }
        else
            city.setError(null);
        if(instate.isEmpty())
        {
            state.setError("Field can't be empty");
            return false;
        }
        else
            state.setError(null);
        if(inland.isEmpty())
        {
            landmark.setError("Field can't be empty");
            return false;
        }
        else
            landmark.setError(null);
        if(inweb.isEmpty())
        {
            website.setError("Field can't be empty");
            return false;
        }
        else
            website.setError(null);
        if(incontact.isEmpty())
        {
            contact.setError("Field can't be empty");
            return false;
        }
        else
            contact.setError(null);

        return true;
    }

    private void saveDatatoFirebase() {

        data_school_information1 data = new data_school_information1(inname,inaddress,incity,instate,inland,inweb,incontact);
        documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(FLAG,2);
                editor.apply();
                Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Steps_Screen,info2).commit();

            }
        });
    }
}