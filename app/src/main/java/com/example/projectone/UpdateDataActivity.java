package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectone.Databases.ProjectTable;
import com.example.projectone.Helper.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateDataActivity extends AppCompatActivity {
    TextInputEditText Quantity, Watts, Horsepower;
    TextInputLayout horse;
    TextView NumberId, OPlus, V, VA, A, P, AT, AF, SNUM, SMM, STYPE, GNUM, GMM, GTYPE, MMPlus, CTYPE;
    AutoCompleteTextView autoCompleteTextView1;
    Button update, preview;
    ProjectTable projectTable;
    DatabaseHelper helper;
    private boolean isAutoCompleteItemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_data);
        NumberId = findViewById(R.id.NumberId);
        autoCompleteTextView1 = findViewById(R.id.Items);
        horse = findViewById(R.id.horse);
        update = findViewById(R.id.next);
        preview = findViewById(R.id.preview);
        OPlus = findViewById(R.id.OPlus);
        V = findViewById(R.id.V);
        VA = findViewById(R.id.VA);
        A = findViewById(R.id.A);
        P = findViewById(R.id.P);
        AT = findViewById(R.id.AT);
        AF = findViewById(R.id.AF);
        SNUM = findViewById(R.id.SNUM);
        SMM = findViewById(R.id.SMM);
        STYPE = findViewById(R.id.STYPE);
        GNUM = findViewById(R.id.GNUM);
        GMM = findViewById(R.id.GMM);
        GTYPE = findViewById(R.id.GTYPE);
        MMPlus = findViewById(R.id.MMPlus);
        CTYPE = findViewById(R.id.CTYPE);
        Quantity = findViewById(R.id.Quantity);
        Watts = findViewById(R.id.Watts);
        Horsepower = findViewById(R.id.Horsepower);
        helper = DatabaseHelper.getInstance(this);


            projectTable = (ProjectTable)getIntent().getSerializableExtra("ProjectTable");
            assert projectTable != null;
            Quantity.setText(projectTable.getQuantity());
            autoCompleteTextView1.setText(projectTable.getItem());

        String[] other= new String[]{"Convenience Outlet", "ACU", "Water Heater", "Range",  "Refrigerator"};

        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this, R.layout.drop_down_item, other
        );
        autoCompleteTextView1.setAdapter(adapter1);

        // PROCEED
        String selectedItem = autoCompleteTextView1.getText().toString();
        String selectedWatts = Watts.getText().toString();

        // Your existing validation for "Lighting Outlet"
        if ("Lighting Outlet".equals(selectedItem)) {
            if (!selectedWatts.isEmpty()) {
                if (!selectedItem.isEmpty()) {
                    selectedItem += ", " + selectedWatts + "W";
                } else {
                    selectedItem = selectedWatts;
                }
                autoCompleteTextView1.setText(selectedItem);
            } else {
                Toast.makeText(UpdateDataActivity.this, "Please select 'Lighting Outlet' only", Toast.LENGTH_SHORT).show();
                return; // return if validation fails
            }
        }

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isAutoCompleteItemSelected = true;
                String selectedItem = autoCompleteTextView1.getText().toString();

                if ("ACU".equals(selectedItem)) {
                    horse.setVisibility(View.VISIBLE);
                } else {
                    horse.setVisibility(View.GONE);
                }

                Toast.makeText(UpdateDataActivity.this, autoCompleteTextView1.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ProjectName = getIntent().getStringExtra("ProjectName");
                if(Quantity.getText().toString().isEmpty() || autoCompleteTextView1.getText().toString().isEmpty() || Watts.getText().toString().isEmpty())
                {
                    Toast.makeText(UpdateDataActivity.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    OPlus.setText("1");
                    V.setText("233");
                    VA.setText("3243");
                    A.setText("12.83");
                    P.setText("2");
                    AT.setText("15");
                    AF.setText("50");
                    SNUM.setText("2");
                    SMM.setText("2.0");
                    STYPE.setText("THHN");
                    GNUM.setText("1");
                    GMM.setText("2.0");
                    GTYPE.setText("THW");
                    MMPlus.setText("20");
                    CTYPE.setText("PVC");
                    helper.updateData(projectTable,
                            ProjectName,
                            Quantity.getText().toString()
                            ,autoCompleteTextView1.getText().toString()
                            ,OPlus.getText().toString()
                            ,V.getText().toString()
                            ,VA.getText().toString()
                            ,A.getText().toString()
                            ,P.getText().toString()
                            ,AT.getText().toString()
                            ,AF.getText().toString()
                            ,SNUM.getText().toString()
                            ,SMM.getText().toString()
                            ,STYPE.getText().toString()
                            ,GNUM.getText().toString()
                            ,GMM.getText().toString()
                            ,GTYPE.getText().toString()
                            ,MMPlus.getText().toString()
                            ,CTYPE.getText().toString());
                    Toast.makeText(UpdateDataActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Loadschedule.class);
                startActivity(intent);
            }
        });
    }
}