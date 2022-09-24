package com.example.sprintitappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    //initialise variables

    TextView tsignusername;
    TextView tsignpassword;
    Button bcreateaccount;
    Button bsignin;
    FirebaseAuth fAuth; // authorises Firebase to collect user information


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //state variable usages

        tsignusername = findViewById(R.id.username);
        tsignpassword = findViewById(R.id.password);
        bsignin = findViewById(R.id.signinbutton);
        bcreateaccount = findViewById(R.id.createaccountbutton);

        fAuth = FirebaseAuth.getInstance();

        bcreateaccount.setOnClickListener(view -> {
            createAccount();
        });

        bsignin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        });

    }

    private void createAccount(){
        String name = tsignusername.getText().toString();
        String password = tsignpassword.getText().toString();

        if (TextUtils.isEmpty(name)){
            tsignusername.setError("Username cannot be empty"); // if user has not typed anything, show error message
            tsignusername.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            tsignpassword.setError("Password cannot be empty"); // if user has not typed anything, show error message
            tsignpassword.requestFocus();
        }else{
            fAuth.createUserWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { // create user within a Firebase database
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){ // if user is successfully created
                        Toast.makeText(SignUp.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, LoginScreen.class));
                    }else{ // if user setup fails
                        Toast.makeText(SignUp.this, "Account Creation Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}