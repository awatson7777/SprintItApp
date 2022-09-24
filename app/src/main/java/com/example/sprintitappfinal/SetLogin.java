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

public class SetLogin extends AppCompatActivity {

    //initialise variables

    TextView username1;
    TextView password1;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //state variable usages

        username1 = findViewById(R.id.username);
        password1 = findViewById(R.id.password);
        signin = findViewById(R.id.signinbutton);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username1.getText().toString().equals("username") && password1.getText().toString().equals("password")) { // // login conditions set
                    Toast.makeText(SetLogin.this, "User Verified", Toast.LENGTH_SHORT).show(); // toast shown
                    openHome(); // activity accessed
                } else
                    Toast.makeText(SetLogin.this, "User Could Not Be Verified, Is Your Information Correct?", Toast.LENGTH_SHORT).show(); // doesn't log user in and shows toast
            }
        });

// Layout linked to the xml file, sets the gradient animation background and sets the duration when created
        RelativeLayout relativeLayout = findViewById(R.id.loginlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

    }

    public void openHome() {
        Intent intent = new Intent(this, HomeScreen.class); // opens Homepage function
        startActivity(intent);
    }
}
