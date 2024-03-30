package com.example.projectone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ChangePipeSize extends AppCompatActivity {

    TextInputEditText TTb;
    Button Update;

    TextView Updatedtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe);
        TTb = findViewById(R.id.TT);
        Update = findViewById(R.id.next);
        Updatedtt = findViewById(R.id.Updatedtt);
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref",MODE_PRIVATE);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wiresec = TTb.getText().toString().trim();
                if (wiresec == null) {
                    TTb.setError("Put Some Value");
                } else {

                    Updatedtt.setText("+ 1 - " + wiresec + " mm.sq. THHN Cu. Wire");
                    String second = Updatedtt.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), Loadschedule.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TT", second);
                    editor.apply();
                    intent.putExtra("executeCode2", true); // Pass a boolean indicating that code should be executed
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}