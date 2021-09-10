package com.example.schoool_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class School_login extends AppCompatActivity {

    private TextInputLayout email,password;
    private MaterialButton login;
    private TextView not_reg;
    String uid;
    private String emailinput,passwrdinput;
    FirebaseAuth mauth;
    FirebaseUser currentUser;
    DocumentReference documentReference;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    int flag;
    Fragment temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_login);

        email=findViewById(R.id.log_email);
        password=findViewById(R.id.log_password);
        login=findViewById(R.id.school_login);
        not_reg=findViewById(R.id.not_reg);
        mauth=FirebaseAuth.getInstance();
         currentUser = mauth.getCurrentUser();
         if(currentUser!=null)
         {
             uid=currentUser.getUid();
             documentReference=db.collection("schools").document(uid);
         }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword())
                    return;
                else
                {
                    findViewById(R.id.log_progressdot).setVisibility(View.VISIBLE);
                    loginWithFirebase();
                }
            }
        });
        not_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(School_login.this,School_signup.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null)
        {
            startActivity(new Intent(School_login.this,Steps_screen.class));
            finish();
        }
    }


    private void loginWithFirebase() {
        mauth.signInWithEmailAndPassword(emailinput,passwrdinput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    findViewById(R.id.log_progressdot).setVisibility(View.GONE);
                    startActivity(new Intent(School_login.this,Steps_screen.class));
                    finish();
                }
                else if(!task.isSuccessful())
                {
                    findViewById(R.id.log_progressdot).setVisibility(View.GONE);
                    Snackbar.make(login,task.getException().getMessage().toString(),Snackbar.LENGTH_SHORT).setBackgroundTint(Color.RED).show();
                }
            }
        });
    }

    private boolean validatePassword() {
        passwrdinput=password.getEditText().getText().toString().trim();
        if(passwrdinput.isEmpty())
        {
            password.setError("Password Required");
            return false;
        }
        else
        {
                password.setError(null);
                return true;
        }
    }

    private boolean validateEmail() {
        emailinput=email.getEditText().getText().toString().trim();
        if(emailinput.isEmpty())
        {
            email.setError("Email Required");
            return false;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailinput).matches())
        {
            email.setError("Invalid Email");
            return false;
        }
        else
        {
            email.setError(null);
            return true;
        }
    }
}