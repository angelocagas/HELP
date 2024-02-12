package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ChangePVC extends AppCompatActivity {

    TextInputEditText NumberPVC;
    Button Update;
    TextView UpdatedPVC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pvc);
        NumberPVC = findViewById(R.id.NumberPVC);
        Update = findViewById(R.id.next);
        UpdatedPVC = findViewById(R.id.NumPVC);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Value = NumberPVC.getText().toString().trim();
                if (Value == null)
                {
                    NumberPVC.setError("Put Some Value");
                }
                else
                {
                    UpdatedPVC.setText(" (G)In "+ Value +" mmø IMC PIPE");
                    String PVCUPDATED = UpdatedPVC.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), Loadschedule.class);
                    intent.putExtra("NUMPVC",PVCUPDATED);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}