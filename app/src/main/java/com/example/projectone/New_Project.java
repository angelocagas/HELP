package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class New_Project extends AppCompatActivity {
    Button proceed;
    TextInputEditText name, Quantity;
    TextView CN,PB,WFG;
    RadioButton bone,btwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        CN = findViewById(R.id.CN);
        PB = findViewById(R.id.PB);
        WFG = findViewById(R.id.WFG);
        Quantity = findViewById(R.id.circuit_num);
        proceed = findViewById(R.id.proceed);
        name = findViewById(R.id.name);
        bone = findViewById(R.id.one);
        btwo = findViewById(R.id.two);
        proceed.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (Quantity.getText().toString().isEmpty() || name.getText().toString().isEmpty())
                {
                    Toast.makeText(New_Project.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                int quantityValu = Integer.parseInt(Quantity.getText().toString().trim());
                if (quantityValu > 30) {
                    Toast.makeText(New_Project.this, "The maximum quantity is 30", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution if quantity is greater than 30
                }

                // Check if a radio button in the second group is selected
                if (!bone.isChecked() && !btwo.isChecked()) {
                    Toast.makeText(New_Project.this, "Please select a Wire for Ground option", Toast.LENGTH_SHORT).show();
                    return;
                }
                String quantityText = Quantity.getText().toString().trim();
                int quantityValue = Integer.parseInt(quantityText);
                if (quantityValue >= 25) {
                    // Quantity is greater than 25, show error toast
                    Toast.makeText(New_Project.this, "It may give some error in 25 Circuit", Toast.LENGTH_SHORT).show();
                }

                if (bone.isChecked())
                {
                    WFG.setText("TW");
                    PB.setText("1PB");
                    String WireForGround = WFG.getText().toString().trim();
                    String PanelBoard = PB.getText().toString().trim();
                    String ProjectName = name.getText().toString().trim();
                    String CircuitNum = CN.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), Inputing.class);
                    intent.putExtra("ProjectName",ProjectName);
                    intent.putExtra("WFG",WireForGround);
                    intent.putExtra("PB",PanelBoard);
                    intent.putExtra("CNM",CircuitNum);
                    startActivity(intent);
                }
                if (btwo.isChecked())
                {
                    WFG.setText("THHN");
                    PB.setText("#PB");
                    String WireForGround = WFG.getText().toString().trim();
                    String PanelBoard = PB.getText().toString().trim();
                    String ProjectName = name.getText().toString().trim();
                    String CircuitNum = CN.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), Inputing.class);
                    intent.putExtra("ProjectName",ProjectName);
                    intent.putExtra("WFG",WireForGround);
                    intent.putExtra("PB",PanelBoard);
                    intent.putExtra("CNM",CircuitNum);
                    startActivity(intent);
                }



            }
        });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        Toast.makeText(this, "You can't back the application until the project is done", Toast.LENGTH_SHORT).show();
    }
}