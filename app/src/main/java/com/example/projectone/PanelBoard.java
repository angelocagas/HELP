package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PanelBoard extends AppCompatActivity {
    Button next;
    TextInputEditText NumberPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_board);
        next = findViewById(R.id.next);
        NumberPanel = findViewById(R.id.NumberPanel);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int enteredValue = 0;
                try {
                    // Try to parse the entered value as an integer
                    enteredValue = Integer.parseInt(NumberPanel.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle the case where the entered value is not a valid integer
                    Toast.makeText(PanelBoard.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (enteredValue > 4) {
                    // Show a toast message if the entered value is greater than 4
                    Toast.makeText(PanelBoard.this, "Maximum is 4 Panel Board", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to the next activity if the condition is not met
                    Intent intent = new Intent(getApplicationContext(), New_Project.class);
                    startActivity(intent);
                }
            }
        });
    }
}