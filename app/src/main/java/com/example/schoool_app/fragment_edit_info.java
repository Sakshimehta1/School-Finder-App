package com.example.schoool_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class fragment_edit_info extends Fragment {

    TextInputLayout editfees,edit_strength;
    AutoCompleteTextView editadmission;
    MaterialButton update;
    ProgressBar progressBar;
    String uid;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseUser currentUser;
    DocumentReference documentReference;
    data_complete data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);
        update=view.findViewById(R.id.update_info);
        progressBar=view.findViewById(R.id.progress_circular);
        editadmission=view.findViewById(R.id.edit_admission);
        editfees=view.findViewById(R.id.edit_fees);
        edit_strength=view.findViewById(R.id.edit_strength);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid=currentUser.getUid();
        editadmission.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.simple_list,getResources().
                getStringArray(R.array.adm_open)));
        documentReference=db.collection("schools").document(uid);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    progressBar.setVisibility(View.VISIBLE);
                    updateDatatoFirebase();
            }
        });
    }


    private void updateDatatoFirebase() {
        String fees,admopen,strength;
        fees=editfees.getEditText().getText().toString();
        admopen=editadmission.getText().toString();
        strength=edit_strength.getEditText().getText().toString();

        documentReference.update("admOpen",admopen,
                "fees",fees,
                "strength",strength).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getData()
    {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    data= documentSnapshot.toObject(data_complete.class);
                    final String prevStrength=data.getStrength();
                    final String prev_fees=data.getFees();
                    final String prev_adm=data.getAdmOpen();

                    editadmission.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            editadmission.setHint(prev_adm);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    edit_strength.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            edit_strength.getEditText().setHint(prevStrength);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            edit_strength.getEditText().setHint("Strength of Students");
                        }
                    });
                    editfees.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            editfees.setHint(prev_fees);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            editfees.setHint("Average Fees per Annum");
                        }
                    });
                }
            }
        });
    }
}