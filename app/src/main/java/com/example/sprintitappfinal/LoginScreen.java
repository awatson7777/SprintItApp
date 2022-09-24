package com.example.sprintitappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginScreen extends AppCompatActivity {

    TextView username1;
    TextView password1;
    Button createaccount;
    Button signin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        username1 = findViewById(R.id.username);
        password1 = findViewById(R.id.password);
        signin = findViewById(R.id.signinbutton);
        createaccount = findViewById(R.id.createaccountbutton);
        fAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(view -> {
            loginUser();
        });

        createaccount.setOnClickListener(view -> {
            startActivity(new Intent(LoginScreen.this, SignUp.class));
        });

        /*signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("a.watson9") && password.getText().toString().equals("password1")) {
                    Toast.makeText(LoginScreen.this, "User Verified", Toast.LENGTH_SHORT).show();
                    openHome();
                } else
                    Toast.makeText(LoginScreen.this, "User Could Not Be Verified, Is Your Information Correct?", Toast.LENGTH_SHORT).show();
            }
        });*/

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        RelativeLayout relativeLayout = findViewById(R.id.loginlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(LoginScreen.this, SignUp.class));
        }
    }

    private void loginUser(){
        String name = username1.getText().toString();
        String password = password1.getText().toString();

        if (TextUtils.isEmpty(name)){
            username1.setError("Username cannot be empty");
            username1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            password1.setError("Password cannot be empty");
            password1.requestFocus();
        }else{
            fAuth.signInWithEmailAndPassword(name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                    } else {
                        Toast.makeText(LoginScreen.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    public void openHome() {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
