package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.textfield.TextInputEditText;

public class ChangeMain extends AppCompatActivity {

    TextInputEditText AT, AF;
    Button Update;
    TextView UpdateMain, GETA,GETHIGHA;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_main);

        AT = findViewById(R.id.AT);
        AF = findViewById(R.id.AF);
        Update = findViewById(R.id.next);
        UpdateMain = findViewById(R.id.UpdatedMain);
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref",MODE_PRIVATE);




            Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AMT = AT.getText().toString().trim();
                String AMF = AF.getText().toString().trim();
                if (AMT == null) {
                    AT.setError("Put Some Value For AMPERE TRIP");
                }
                if (AMF == null) {
                    AF.setError("Put Some Value For AMPERE FRAME");
                }
                else {
                    UpdateMain.setText(AMT + " AT, " + AMF + " AF, 2P, 230V, 60 GHZ");
                    String updatedMainText = UpdateMain.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), Loadschedule.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UMT", updatedMainText);
                    editor.apply();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}