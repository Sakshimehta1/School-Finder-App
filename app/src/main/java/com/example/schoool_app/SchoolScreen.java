package com.example.schoool_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SchoolScreen extends AppCompatActivity{

    Fragment temp;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    BottomNavigationView btmnav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_screen);
        temp=null;
        btmnav=findViewById(R.id.btmnav);

        btmnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.info: temp=new fragment_edit_info();
                        getSupportFragmentManager().beginTransaction().replace(R.id.SchoolFrame,temp).commit();
                    break;
                    case R.id.addPhoto: temp=new Fragment_school_add_photo();
                        getSupportFragmentManager().beginTransaction().replace(R.id.SchoolFrame,temp).commit();
                    break;
                    case R.id.review: temp=new fragment_review();
                        getSupportFragmentManager().beginTransaction().replace(R.id.SchoolFrame,temp).commit();
                    break;
                }

                return true;
            }
        });
    }

}