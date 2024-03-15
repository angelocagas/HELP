package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView login, error;
    Button submit;
    TextInputEditText name, phone, email, password;
    ProgressBar bar;
    FirebaseAuth firebaseAuth;

    String UserId;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login);
        error = findViewById(R.id.error);
        bar = findViewById(R.id.bar);
        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), Menu.class));
            finish();
        }

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Set the initial text when the phone EditText gains focus
                if (hasFocus) {
                    String currentText = phone.getText().toString();
                    if (TextUtils.isEmpty(currentText) || !currentText.startsWith("+639")) {
                        phone.setText("+639");
                    }
                }
            }
        });

        login.setOnClickListener(view -> {
            Intent intent  = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        submit.setOnClickListener(view -> {
            bar.setVisibility(View.VISIBLE);
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String nm = name.getText().toString().trim();
            String pon = phone.getText().toString().trim();

            if (!isValidEmail(mail))
            {
                email.setError("You must enter valid email");
                bar.setVisibility(View.GONE);
                return;
            }
            if(password.length()<=7)
            {
                password.setError("Password must have 8 characters");
                bar.setVisibility(View.GONE);
                return;
            }
            if (phone.length()!=13)
            {
                phone.setError("ex. +639123456789");
                bar.setVisibility(View.GONE);
                return;
            }
            if (name.length()<=3)
            {
                email.setError("Name must have 4 characters");
                bar.setVisibility(View.GONE);
                return;
            }
            if (TextUtils.isEmpty(nm))
            {
                name.setError("Name must have 4 characters");
                bar.setVisibility(View.GONE);
                return;
            }
            if (nm.isEmpty() || pon.isEmpty() || mail.isEmpty() || pass.isEmpty())
            {
                error.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        error.setVisibility(View.GONE);
                        bar.setVisibility(View.GONE);

                    }
                },3000);

                return;

            }
            else
            {
                firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        { 
                            bar.setVisibility(View.GONE);
                            UserId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users").document(UserId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Fullname",nm);
                            user.put("Contact",pon);
                            user.put("Email",mail);
                            documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(getApplicationContext(),Menu.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            bar.setVisibility(View.GONE);
                            Toast.makeText(Register.this, "Registration Failed. Try another email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private boolean isValidEmail(CharSequence target)
    {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}