package com.example.schoool_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class fragment_school_info2xml extends Fragment {
    TextInputLayout strength,estbyear;
    AutoCompleteTextView admopen ,gradesfrom,gradeupto,board,schooltype;
    MaterialButton Save;
    String inadmopen,ingradesfrom,ingradeto,instrength, inboard,inEstbyear, inschooltype;
    String uid;
    ProgressBar progressBar;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    public static final String SHARED_PREF="sharedPrefs";
    public static final String FLAG="flag";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_info2xml, container, false);
        admopen=view.findViewById(R.id.autoadm);
        gradesfrom=view.findViewById(R.id.gradesfrom);
        gradeupto=view.findViewById(R.id.gradeto);
        schooltype=view.findViewById(R.id.schooltype);
        board=view.findViewById(R.id.board);
        Save=view.findViewById(R.id.save2);
        strength=view.findViewById(R.id.strength);
        estbyear= view.findViewById(R.id.establishyear);

        Save=view.findViewById(R.id.save2);

        progressBar=view.findViewById(R.id.progress2);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid=currentUser.getUid();
        documentReference=db.collection("schools").document(uid);
        admopen.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.simple_list,getResources().
                getStringArray(R.array.adm_open)));
        gradesfrom.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.simple_list,getResources().
                getStringArray(R.array.grades_from)));
        gradeupto.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.simple_list,getResources().
                getStringArray(R.array.gradeupto)));
        board.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.simple_list,getResources().
                getStringArray(R.array.board)));
        schooltype.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.simple_list,getResources().
                getStringArray(R.array.school_type)));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Save.setOnClickListener(new View.OnClickListener() {
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
        inadmopen=admopen.getText().toString();
        ingradesfrom=gradesfrom.getText().toString();
        ingradeto=gradeupto.getText().toString();
        inboard=board.getText().toString();
        inschooltype=schooltype.getText().toString();
        inEstbyear=estbyear.getEditText().getText().toString();
        instrength=strength.getEditText().getText().toString();

        if(inadmopen.isEmpty())
        {
            admopen.setError("Field can't be empty");
            return false;
        }
        else
            admopen.setError(null);
        if(ingradesfrom.isEmpty())
        {
            gradesfrom.setError("Field can't be empty");
            return false;
        }
        else
            gradesfrom.setError(null);
        if(ingradeto.isEmpty())
        {
            gradeupto.setError("Field can't be empty");
            return false;
        }
        else
            gradeupto.setError(null);

        if(instrength.isEmpty())
        {
            strength.setError("Field can't be empty");
            return false;
        }
        else
            strength.setError(null);

        if(inboard.isEmpty())
        {
            board.setError("Field can't be empty");
            return false;
        }
        else
            board.setError(null);

        if(inEstbyear.isEmpty())
        {
            estbyear.setError("Field can't be empty");
            return false;
        }
        else
            estbyear.setError(null);

        if(inschooltype.isEmpty())
        {
            schooltype.setError("Field can't be empty");
            return false;
        }
        else
            schooltype.setError(null);
        return true;

    }

    private void saveDatatoFirebase() {

        data_school_info2 data = new data_school_info2(inadmopen,ingradesfrom,ingradeto,instrength,inboard,inEstbyear,inschooltype);
        // object -> Map
        ObjectMapper oMapper = new ObjectMapper();

        Map<String, Object> map = oMapper.convertValue(data, Map.class);
        documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(FLAG,3);
                editor.apply();

                Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Steps_Screen,new fragment_school_info3xml()).commit();

            }
        });
    }


}