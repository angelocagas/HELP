package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.example.projectone.Helper.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Inputing extends AppCompatActivity {
    // Declare ArrayList to store "A" values
    ArrayList<Double> arrayAmp = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView1, Horsepower;
    TextInputLayout horses;
    TextView HighestAmp12, CNM,TotalVA, TotalA, others, CircuitNum, OPlus, V, VA, A, P, AT, AF, SNUM, SMM, STYPE, GNUM, GMM, GTYPE, MMPlus, CTYPE;
    Button next, preview,back;
    TextInputEditText Quantity, Watts, Others;
    DatabaseHelper helper;
    private boolean isAutoCompleteItemSelected = false;
    int counter = 1;
    private SharedPreferences sharedPreferences;
    private DecimalFormat decimalFormat;
    double totalVAValue = 0.00;
    double totalAValue = 0.00;
    int cirnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        others = findViewById(R.id.others);
        counter = sharedPreferences.getInt("counter", counter);
        setContentView(R.layout.activity_inputing);
        others = findViewById(R.id.others);
        helper = DatabaseHelper.getInstance(this);
        CircuitNum = findViewById(R.id.CircuitNum);
        autoCompleteTextView1 = findViewById(R.id.Items);
        horses = findViewById(R.id.horses);
        next = findViewById(R.id.next);
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
        back = findViewById(R.id.Back);
        TotalA = findViewById(R.id.TotalA);
        TotalVA = findViewById(R.id.TotalVA);
        CNM = findViewById(R.id.CNM);
        HighestAmp12 = findViewById(R.id.HighestAmp);


        Intent intent = getIntent();
        String Cirnum = intent.getStringExtra("CNM");
        CNM.setText(Cirnum);

        try {
            cirnum = Integer.parseInt(CNM.getText().toString().trim());

        } catch (NumberFormatException e) {

        }







        String[] other = new String[]{"Lighting Outlet", "Convenience Outlet", "ACU", "Water Heater", "Range", "Refrigerator","Spare"};
        String[] hp = new String[]{"1/6", "1/4", "1/3", "0.5", "0.75", "1", "1.5", "2", "3", "5", "7.5", "10"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.drop_down_item, other);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.drop_down_item, hp);
        autoCompleteTextView1.setAdapter(adapter1);
        Horsepower.setAdapter(adapter2);


        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isAutoCompleteItemSelected = true;
                String selectedItem = autoCompleteTextView1.getText().toString();
                if ("Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {
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
                    Quantity.setText("1");
                } else {
                    horses.setVisibility(View.GONE);
                }
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

        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (counter == cirnum)
                {

                    Toast.makeText(Inputing.this, "You exceed the limit of Circuit Number", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(getApplicationContext(),Loadschedule.class);
                   startActivity(intent);

                }
                computeVA();
                computeA();
                counter++;
                CircuitNum.setText("CIRCUIT NO# " + counter);
                String ProjectName = getIntent().getStringExtra("ProjectName");
                String WireForGround = getIntent().getStringExtra("WFG");

                if (!isAutoCompleteItemSelected || Quantity.getText().toString().isEmpty() || Watts.getText().toString().isEmpty()) {
                    Toast.makeText(Inputing.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
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
                            Toast.makeText(Inputing.this, "Please select 'Lighting Outlet' only", Toast.LENGTH_SHORT).show();
                            return; // return if validation fails
                        }
                    }



                    // Additional validation for "ACU"
                    String selectedHP = Horsepower.getText().toString();
                    if ("ACU".equals(selectedItem)) {
                        if (!selectedHP.isEmpty()) {
                            if (!selectedItem.isEmpty()) {
                                selectedItem += " " + selectedHP + "HP";
                            } else {
                                selectedItem = selectedHP;
                            }
                            autoCompleteTextView1.setText(selectedItem);
                        } else {
                            Toast.makeText(Inputing.this, "Please select 'Lighting Outlet' only", Toast.LENGTH_SHORT).show();
                            return; // return if validation fails
                        }
                    }
                    OPlus.setText("1");//MATIC
                    V.setText("230");//MATIC
                    P.setText("2");//MATIC
                    AF.setText("50");//MATIC
                    MMPlus.setText("20");//MATIC
                    CTYPE.setText("PVC");//MATIC
                    helper.addNewProject(
                            ProjectName,
                            Quantity.getText().toString(),
                            autoCompleteTextView1.getText().toString(),
                            OPlus.getText().toString(),
                            V.getText().toString(),
                            VA.getText().toString(),
                            A.getText().toString(),
                            P.getText().toString(),
                            AT.getText().toString(),
                            AF.getText().toString(),
                            SNUM.getText().toString(),
                            SMM.getText().toString(),
                            STYPE.getText().toString(),
                            GNUM.getText().toString(),
                            GMM.getText().toString(),
                            GTYPE.getText().toString(),
                            MMPlus.getText().toString(),
                            CTYPE.getText().toString()
                    );
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("counter", counter);
                editor.apply();
                Quantity.setText(null);
                autoCompleteTextView1.setText(null);
                Watts.setText(null);
                Horsepower.setText(null);
                others.setText(null);
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double highestAmp = findHighestAmp();
                HighestAmp12.setText(String.valueOf(highestAmp));

                // Once the highest values are determined, create an intent to start the new activity
                String passVA = TotalVA.getText().toString();
                String passA = TotalA.getText().toString();
                String passHIGHEST = HighestAmp12.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Loadschedule.class);

                // Pass the necessary data to the new activity through the intent
                intent.putExtra("TOTALVA", passVA);
                intent.putExtra("TOTALA", passA);
                intent.putExtra("HIGHA", passHIGHEST);

                // Start the new activity with the intent
                startActivity(intent);
            }
        });

    }

    private void computeVA() {
        if (!Quantity.getText().toString().isEmpty() && !Watts.getText().toString().isEmpty()) {
            double quantityValue = Double.parseDouble(Quantity.getText().toString());
            double wattsValue = Double.parseDouble(Watts.getText().toString());
            double vaValue = quantityValue * wattsValue;
            // Update the VA TextView with the computed value
            VA.setText(decimalFormat.format(vaValue));
            totalVAValue += vaValue;
            updateTotalVA();
        }
    }
    private void updateTotalVA() {
        // Update the TotalVA TextView with the total VA value
        TotalVA.setText(decimalFormat.format(totalVAValue));
    }
    private void computeA() {
        if (!VA.getText().toString().isEmpty()) {
            double vaValue = Double.parseDouble(VA.getText().toString());
            double aValue = vaValue / 230.00;

            DecimalFormat aFormat = new DecimalFormat("#.##");
            aFormat.setRoundingMode(RoundingMode.HALF_UP);
            A.setText(aFormat.format(aValue));
            totalAValue += aValue;

            // Update the total A
            updateTotalA();


            // Add "A" value to the arrayAmp ArrayList based on selected item
            String selectedItem = autoCompleteTextView1.getText().toString();
            if ("ACU".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {
                arrayAmp.add(aValue);
            } else if ("Lighting Outlet".equals(selectedItem)) {
                // If selected item is "Lighting Outlet", add its "A" value to arrayAmp
                arrayAmp.add(aValue);
            }
        }
    }
    // Function to find the highest value in the arrayAmp ArrayList

    private void updateTotalA() {
        // Update the TotalA TextView with the total A value
        totalAValue = Double.parseDouble(decimalFormat.format(totalAValue)); // Ensure totalAValue has two decimal places
        TotalA.setText(decimalFormat.format(totalAValue));
    }


    private double findHighestAmp() {
        double highestAmp = 0.0;
        for (Double amp : arrayAmp) {
            if (amp > highestAmp) {
                highestAmp = amp;
            }
        }

        // Create DecimalFormat object to format the number
        DecimalFormat df = new DecimalFormat("#.##");
        String roundedValue = df.format(highestAmp);

        // Parse the rounded value back to double
        return Double.parseDouble(roundedValue);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't back the application until the project is done", Toast.LENGTH_SHORT).show();
    }

}
