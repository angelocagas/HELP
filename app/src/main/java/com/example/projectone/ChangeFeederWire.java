package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public  class ChangeFeederWire extends AppCompatActivity {

    RadioButton bone,btwo;
    Button next;
    TextView WFGT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_feeder_wire);


        bone = findViewById(R.id.one);
        btwo = findViewById(R.id.two);
        next = findViewById(R.id.next);
        WFGT = findViewById(R.id.WFGT);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bone.isChecked()) {
                    // Set wire type to TW
                    WFGT.setText("TW");
                    sendWireTypeToLoadSchedule("TW");
                } else if (btwo.isChecked()) {
                    // Set wire type to THHN
                    WFGT.setText("THHN");
                    sendWireTypeToLoadSchedule("THHN");
                } else {
                    Toast.makeText(ChangeFeederWire.this, "Choose wire for Ground", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendWireTypeToLoadSchedule(String wireType) {
        // Send the selected wire type to Loadschedule activity
        String TYPE = WFGT.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), Loadschedule.class);
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UMT", TYPE);
        editor.apply();
        startActivity(intent);
        finish();
    }
}
