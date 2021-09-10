package com.example.schoool_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class School_signup extends AppCompatActivity {

    private TextInputLayout email,password;
    private MaterialButton signup;
    private TextView registered;
    private String emailinput,passwrdinput;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_signup);
        email=findViewById(R.id.school_signup_email);
        password=findViewById(R.id.school_signup_password);
        signup=findViewById(R.id.school_signup);
        registered=findViewById(R.id.registered);
        mauth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword())
                    return;
                else
                {
                    findViewById(R.id.progressdot).setVisibility(View.VISIBLE);
                    signUpwithFirebase();
                }
            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(School_signup.this,School_login.class));
                finish();
            }
        });
    }

    private void signUpwithFirebase() {
        mauth.createUserWithEmailAndPassword(emailinput,passwrdinput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user=mauth.getCurrentUser();
                    startActivity(new Intent(School_signup.this,School_login.class));
                    finish();
                }
                else if(!task.isSuccessful())
                {
                    findViewById(R.id.progressdot).setVisibility(View.GONE);
                    Snackbar.make(signup,task.getException().getMessage().toString(),Snackbar.LENGTH_SHORT).setBackgroundTint(Color.RED).show();
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
            Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})").matcher(passwrdinput);
            if(!matcher.matches())
            {
                password.setError("Weak Password");
                return false;
            }
            else
            {
                password.setError(null);
                return true;
            }
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