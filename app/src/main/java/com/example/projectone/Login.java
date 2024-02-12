package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView register;
    ImageView tray;
    Button login;
    TextInputEditText email,password;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        tray = findViewById(R.id.tray);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.loading);

        if (firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), Menu.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (!isValidEmail(mail))
                {
                    email.setError("You must enter valid email");

                }
                if(password.length()<=7)
                {
                    password.setError("Password must have 8 characters");
                }
                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass))
                {
                    Toast.makeText(Login.this, "You must enter email and password", Toast.LENGTH_SHORT).show();
                }
                else {
                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), Menu.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Incorrect Password or Email", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });}

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }
    private boolean isValidEmail(CharSequence target)
    {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}