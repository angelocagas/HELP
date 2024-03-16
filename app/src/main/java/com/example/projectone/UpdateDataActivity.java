package com.example.projectone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UpdateDataActivity extends AppCompatActivity {
    ArrayList<Double> arrayAmp = new ArrayList<>();
    private String CTR;
    TextInputEditText Quantity, Watts;
    TextInputLayout horses;
    TextView NumberId, OPlus,TotalVA, TotalA, V, VA, A, P, AT, AF, SNUM, SMM, STYPE, GNUM, GMM, GTYPE, MMPlus, CTYPE;
    AutoCompleteTextView autoCompleteTextView1, Horsepower;
    Button update, preview;
    ProjectTable projectTable;
    DatabaseHelper helper;
    private DecimalFormat decimalFormat;
    double totalVAValue = 0.00;
    double totalAValue = 0.00;
    private boolean isAutoCompleteItemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_data);
        NumberId = findViewById(R.id.NumberId);
        autoCompleteTextView1 = findViewById(R.id.Items);
        horses = findViewById(R.id.horses);
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
        Horsepower = findViewById(R.id.horse);
        TotalA = findViewById(R.id.TotalA);
        TotalVA = findViewById(R.id.TotalVA);
        helper = DatabaseHelper.getInstance(this);

        Intent intent = getIntent();




            String totalVA = intent.getStringExtra("TOTALVA");
            String totalA = intent.getStringExtra("TOTALA");
            String HIGHA = intent.getStringExtra("HIGHA");
            String CTR = intent.getStringExtra("CTR");







            projectTable = (ProjectTable)getIntent().getSerializableExtra("ProjectTable");
            assert projectTable != null;
            Quantity.setText(projectTable.getQuantity());
            autoCompleteTextView1.setText(projectTable.getItem());

        /*if (projectTable.getItem().startsWith("Lighting Outlet")) {
            autoCompleteTextView1.setText("Lighting Outlet");
        }
        else if (projectTable.getItem().startsWith("ACU")) {
                autoCompleteTextView1.setText("ACU");
                horses.setVisibility(View.VISIBLE);
            }
        else{
            autoCompleteTextView1.setText(projectTable.getItem());

        }*/



//adapter for ITEMS
        String[] other = new String[]{"Lighting Outlet", "Convenience Outlet", "ACU", "Water Heater", "Range", "Refrigerator","Spare"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.drop_down_item, other);
        autoCompleteTextView1.setAdapter(adapter1);

//adapter(dropdown) for Horse power
        String[] hp = new String[]{"1/6", "1/4", "1/3", "0.5", "0.75", "1", "1.5", "2", "3", "5", "7.5", "10"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.drop_down_item, hp);
        Horsepower.setAdapter(adapter2);


        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isAutoCompleteItemSelected = true;
                String selectedItem = autoCompleteTextView1.getText().toString();
                if ("Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "Refrigerator".equals(selectedItem) )  {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    AT.setText("30");
                }
                if ("Convenience Outlet".equals(selectedItem) || "ACU".equals(selectedItem) || "Spare".equals(selectedItem)) {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    AT.setText("20"); // Set the default value for other items
                }
                if ("Convenience Outlet".equals(selectedItem) || "Refrigerator".equals(selectedItem) || "ACU".equals(selectedItem)) {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    SMM.setText("3.5");
                    GMM.setText("3.5");
                }
                if ("Water Heater".equals(selectedItem) ||  "Range".equals(selectedItem)) {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    SMM.setText("5.5");
                    GMM.setText("5.5");
                }
                if ("Lighting Outlet".equals(selectedItem)) {
                    // If the user chooses Lighting Outlet, set the value of AT to 15
                    AT.setText("15");
                    SMM.setText("2");
                    GMM.setText("2");

                }

                if ("Spare".equals(selectedItem)) {
                    // If the user chooses Lighting Outlet, set the value of AT to 15
                    SMM.setText("Stub");
                    GMM.setText("");
                }

                if ("Lighting Outlet".equals(selectedItem) || "Convenience Outlet".equals(selectedItem) ||"Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "ACU".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    SNUM.setText("2");//MATIC
                    GNUM.setText("1");//MATIC
                    STYPE.setText("THHN");//MATIC
                    GTYPE.setText("THW");//MATIC
                } else{
                    SNUM.setText("");//MATIC
                    GNUM.setText("");//MATIC
                    STYPE.setText("UP");//MATIC
                    GTYPE.setText("");//MATIC
                }

                if ("ACU".equals(selectedItem)) {
                    horses.setVisibility(View.VISIBLE);
                } else {
                    horses.setVisibility(View.GONE);
                }

                Toast.makeText(UpdateDataActivity.this, autoCompleteTextView1.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        Horsepower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isAutoCompleteItemSelected = true;
                String selectedItem = Horsepower.getText().toString();

                switch (selectedItem) {
                    case "1/6":
                        Watts.setText("506");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("2.20");
                        VA.setText("506");
                        break;
                    case "1/4":
                        Watts.setText("667");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("2.90");
                        VA.setText("667");
                        break;
                    case "1/3":
                        Watts.setText("828");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("3.60");
                        VA.setText("828");
                        break;
                    case "0.5":
                        Watts.setText("1127");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("4.90");
                        VA.setText("1127");
                        break;
                    case "0.75":
                        Watts.setText("1587");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("6.90");
                        VA.setText("1587");
                        break;
                    case "1":
                        Watts.setText("1840");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("8.00");
                        VA.setText("1840");
                        break;
                    case "1.5":
                        Watts.setText("2300");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("10.00");
                        VA.setText("2300");
                        break;
                    case "2":
                        Watts.setText("2760");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("12.00");
                        VA.setText("2760");
                        break;
                    case "3":
                        Watts.setText("3910");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("17.00");
                        VA.setText("3910");
                        break;
                    case "5":
                        Watts.setText("6440");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("28.00");
                        VA.setText("6440");
                        break;
                    case "7.5":
                        Watts.setText("9220");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("40.00");
                        VA.setText("9220");
                        break;
                    case "10":
                        Watts.setText("11500");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("50.00");
                        VA.setText("11500");
                        break;
                    default:
                        // Default case if none of the above conditions match

                        break;

                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String ProjectName = getIntent().getStringExtra("ProjectName");
                if(Quantity.getText().toString().isEmpty() || autoCompleteTextView1.getText().toString().isEmpty() || Watts.getText().toString().isEmpty())
                {
                    Toast.makeText(UpdateDataActivity.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {



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




                        // Additional validation for "ACU"
                        String selectedHP = Horsepower.getText().toString();

                        //IF ACU IS SELECTED MATING DATA
                        if ("ACU".equals(selectedItem)) {
                            if (!selectedHP.isEmpty()) {
                                if (!selectedItem.isEmpty()) {
                                    selectedItem += " " + selectedHP + "HP";
                                } else {
                                    selectedItem = selectedHP;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            } else {
                                Toast.makeText(UpdateDataActivity.this, "Please select 'Lighting Outlet' only", Toast.LENGTH_SHORT).show();
                                return; // return if validation fails
                            }
                        }



                    OPlus.setText("1");
                    V.setText("233");
                    P.setText("2");
                    AF.setText("50");
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
                            ,CTYPE.getText().toString()

                    );

                    Toast.makeText(UpdateDataActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    Quantity.setText(null);
                    autoCompleteTextView1.setText(null);
                    Watts.setText(null);
                    Horsepower.setText(null);
                    horses.setVisibility(View.GONE);

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