package com.example.schoool_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Steps_screen extends AppCompatActivity {

    DocumentReference documentReference;
    FirebaseUser currentUser;
    String uid;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    int flag;
    Fragment temp;

    public static final String SHARED_PREF="sharedPrefs";
    public static final String FLAG="flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid=currentUser.getUid();
        setContentView(R.layout.activity_steps_screen);
        documentReference=db.collection("schools").document(uid);


    }
    @Override
    protected void onStart() {
        super.onStart();

//        getFlag();
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        if(sharedPreferences!=null)
            flag=sharedPreferences.getInt(FLAG,1);
        else
            flag=1;
//        Toast.makeText(getApplicationContext(), flag, Toast.LENGTH_SHORT).show();
        if(flag==4)
        {
            startActivity(new Intent(Steps_screen.this,SchoolScreen.class));
            finish();
        }
        else if(flag==1)
        {
            temp=new Fragment_school_info1();
            getSupportFragmentManager().beginTransaction().replace(R.id.Steps_Screen,temp).commit();
        }
        else if(flag==2)
        {
            temp=new fragment_school_info2xml();
            getSupportFragmentManager().beginTransaction().replace(R.id.Steps_Screen,temp).commit();
        }
        else if(flag==3)
        {
            temp= new fragment_school_info3xml();
            getSupportFragmentManager().beginTransaction().replace(R.id.Steps_Screen,temp).commit();
        }
    }
//    private void getFlag() {
//        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
//        flag=sharedPreferences.getInt(FLAG,1);
//    }


}