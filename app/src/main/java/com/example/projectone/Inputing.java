package com.example.projectone;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectone.Databases.ProjectTable;
import com.example.projectone.Helper.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.itextpdf.text.log.Counter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inputing extends AppCompatActivity {
    // Declare ArrayList to store "A" values
    ArrayList<Double> arrayAmp = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView1, Horsepower, Typeofpipe,Wattslo,Quantitylo;
    TextInputLayout horses,hintitem,hintconduit,quant,quantlo,Wat,Watlo;
    TextView CircuitNum2, demand, HighestAmp12, CNM, loadname,TotalVA, TotalA, others, CircuitNum, OPlus, V, VA, A, P, AT, AF, SNUM, SMM, STYPE, GNUM, GMM, GTYPE, MMPlus, CTYPE, Mainpipetxt, TOTALAtxt, TOTALVAtxt;
    Button next, preview, preview2, back, update;
    TextInputEditText Quantity, Watts, Others, editTextLoadname;
    DatabaseHelper helper;
    private boolean isAutoCompleteItemSelected = false;
    private boolean isAutoCompleteLOSelected = false;
    private boolean isAutoCompletePipeSelected = false;


    private SharedPreferences sharedPreferences;
    private DecimalFormat decimalFormat;
    double totalVAValue = 0.00;
    private AlertDialog dialog;
    double totalAValue = 0.00;
    int cirnum;

    ProjectTable projectTable;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Clear the SharedPreferences when the activity is first initialized
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        others = findViewById(R.id.others);



        setContentView(R.layout.activity_inputing);
        others = findViewById(R.id.others);
        helper = DatabaseHelper.getInstance(this);
        CircuitNum = findViewById(R.id.CircuitNum);
        autoCompleteTextView1 = findViewById(R.id.Items);
        horses = findViewById(R.id.horses);
        hintitem = findViewById(R.id.hintitem);
      //  quant = findViewById(R.id.quant);
        Watlo= findViewById(R.id.Watlo);
       // quantlo = findViewById(R.id.quantlo);
        Wat = findViewById(R.id.Wat);
        Wattslo = findViewById(R.id.Wattslo);
        hintconduit = findViewById(R.id.hintconduit);
        next = findViewById(R.id.next);
        preview = findViewById(R.id.preview);
        loadname = findViewById(R.id.loadname);
        preview2 = findViewById(R.id.preview2);
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
        Typeofpipe = findViewById(R.id.Typeofpipe);
        Horsepower = findViewById(R.id.horse);
        update = findViewById(R.id.update);
        TotalA = findViewById(R.id.TotalA);
        TotalVA = findViewById(R.id.TotalVA);
        CNM = findViewById(R.id.CNM);
        HighestAmp12 = findViewById(R.id.HighestAmp);
        demand = findViewById(R.id.demand);
        Mainpipetxt = findViewById(R.id.Mainpipe);
        TOTALAtxt = findViewById(R.id.TOTALA);
        TOTALVAtxt = findViewById(R.id.TOTALVA);
        CircuitNum2 = findViewById(R.id.TOTALVA);

        Intent intent = getIntent();
        String Cirnum = intent.getStringExtra("CNM");
        CNM.setText(Cirnum);
        counter();





        try {
            cirnum = Integer.parseInt(CNM.getText().toString().trim());

        } catch (NumberFormatException e) {

        }


        // Check if editing mode is enabled
        boolean isEditMode = getIntent().getBooleanExtra("EditMode", false);
        if (isEditMode) {


            projectTable = (ProjectTable) getIntent().getSerializableExtra("ProjectTable");
            assert projectTable != null;


            Quantity.setText(projectTable.getQuantity());
            String quan = projectTable.getQuantity();
            String item = projectTable.getItem();
            String VAs = projectTable.getVA();
            String Ctype = projectTable.getCTYPE();

            CircuitNum.setVisibility(View.GONE);
            // If editing mode is enabled, disable the "Next" button
            preview.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            preview2.setVisibility(View.VISIBLE);
            update.setVisibility(View.VISIBLE);



            // Check if item starts with "LIGHTING OUTLET"
            if (item.startsWith("Lighting Outlet")) {
                autoCompleteTextView1.setText("Lighting Outlet");
                Wat.setVisibility(View.GONE);
                Watlo.setVisibility(View.VISIBLE);

                int startIndex = item.indexOf(',');
                int endIndex = item.indexOf('W');
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    // Extract the interval
                    String interval = item.substring(startIndex + 1, endIndex).trim();

                    if (interval.equals("100")) {
                        Wattslo.setText("100");
                    } else if (interval.equals("95")) {
                        Wattslo.setText("95");
                    } else if (interval.equals("90")) {
                        Wattslo.setText("90");
                    } else if (interval.equals("85")) {
                        Wattslo.setText("85");
                    } else if (interval.equals("80")) {
                        Wattslo.setText("80");
                    } else if (interval.equals("75")) {
                        Wattslo.setText("75");
                    } else if (interval.equals("70")) {
                        Wattslo.setText("70");
                    } else if (interval.equals("65")) {
                        Wattslo.setText("65");
                    } else if (interval.equals("60")) {
                        Wattslo.setText("60");
                    } else if (interval.equals("55")) {
                        Wattslo.setText("55");
                    } else if (interval.equals("50")) {
                        Wattslo.setText("50");
                    } else if (interval.equals("45")) {
                        Wattslo.setText("45");
                    } else if (interval.equals("40")) {
                        Wattslo.setText("40");
                    } else if (interval.equals("35")) {
                        Wattslo.setText("35");
                    } else if (interval.equals("30")) {
                        Wattslo.setText("30");
                    } else if (interval.equals("25")) {
                        Wattslo.setText("25");
                    } else if (interval.equals("20")) {
                        Wattslo.setText("20");
                    } else if (interval.equals("15")) {
                        Wattslo.setText("15");
                    } else if (interval.equals("10")) {
                        Wattslo.setText("10");
                    } else if (interval.equals("5")) {
                        Wattslo.setText("5");
                    } else {
                        Wattslo.setText("");
                    }
                }
            }

            // Check if item starts with "ACU"
            else if (item.startsWith("ACU")) {
                autoCompleteTextView1.setText("ACU");
                horses.setVisibility(View.VISIBLE);
                //for HORSEPOWER DISPLAY
                int startIndex = item.indexOf('U');
                int endIndex = item.indexOf("HP");
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    // Extract the interval
                    String interval = item.substring(startIndex + 1, endIndex).trim();

                    if(interval.equals("1/6")){
                        Horsepower.setText("1/6");
                    } else if(interval.equals("1/4")){
                        Horsepower.setText("1/4");

                    } else if(interval.equals("1/3")){
                        Horsepower.setText("1/3");

                    } else if(interval.equals("1/2")){
                        Horsepower.setText("1/2");

                    } else if(interval.equals("3/4")){
                        Horsepower.setText("3/4");

                    } else if(interval.equals("1")){
                        Horsepower.setText("1");

                    } else if(interval.equals("1 1/2")){
                        Horsepower.setText("1 1/2");

                    } else if(interval.equals("2")){
                        Horsepower.setText("2");

                    } else if(interval.equals("3")){
                        Horsepower.setText("3");

                    } else if(interval.equals("5")){
                        Horsepower.setText("5");

                    } else if(interval.equals("7 1/2")){
                        Horsepower.setText("7 1/2");

                    }else if(interval.equals("10")){
                        Horsepower.setText("10");

                    }else{
                        Horsepower.setText("");

                    }

                }


            } else if (item.startsWith("Convenience Outlet")) {
                autoCompleteTextView1.setText("Convenience Outlet");
            } else if (item.startsWith("Water Heater")) {
                autoCompleteTextView1.setText("Water Heater");
            } else if (item.startsWith("Range")) {
                autoCompleteTextView1.setText("Range");
            } else if (item.startsWith("Refrigerator")) {
                autoCompleteTextView1.setText("Refrigerator");
            } else if (item.startsWith("Spare")) {
                autoCompleteTextView1.setText("Spare");
            }

            //populate type of pipe
            if (Ctype.startsWith("EMT")){
                Typeofpipe.setText("EMT");
                CTYPE.setText("EMT");
            } else if (Ctype.startsWith("PVC")){
                Typeofpipe.setText("PVC");
                CTYPE.setText("PVC");
            } else if(Ctype.startsWith("IMC")){
                Typeofpipe.setText("IMC");
                CTYPE.setText("IMC");
            }


            TextInputEditText descpop = findViewById(R.id.others); // Replace R.id.textInputEditTextId with the actual ID of your TextInputEditText
            TextInputEditText wattspop = findViewById(R.id.Watts); // Replace R.id.textInputEditTextId with the actual ID of your TextInputEditText

            //for watts display
            if (quan.equals("1")) {
                // If quantity is 1, display VAs directly
                wattspop.setText(VAs);
            } else {
                // If quantity is not 1, calculate watts and display the result
                int quantity = Integer.parseInt(quan);
                int vas = Integer.parseInt(VAs);
                int wattsup = vas / quantity;
                wattspop.setText(String.valueOf(wattsup));
            }


            if (item.startsWith("Lighting Outlet") || item.startsWith("Convenience Outlet") || item.startsWith("ACU") || item.startsWith("Water Heater") || item.startsWith("Range") || item.startsWith("Refrigerator") || item.startsWith("Spare")) {
                // Check if the item starts with any of the specified strings

                // Find the opening and closing parentheses
                int startIndex1 = item.indexOf('(');
                int endIndex1 = item.indexOf(')');

                // Extract the text inside the parentheses
                if (startIndex1 != -1 && endIndex1 != -1 && startIndex1 < endIndex1) {
                    String extractedText = item.substring(startIndex1 + 1, endIndex1).trim();
                    descpop.setText(extractedText); // Set the extracted text to a UI element, e.g., descpop
                } else {
                    // If no valid parentheses found, set descpop text to an empty string
                    descpop.setText("");
                }
            }


        } else {
            // Otherwise, enable the "Next" button
            next.setVisibility(View.VISIBLE);
            preview.setVisibility(View.VISIBLE);
            horses.setVisibility(View.GONE);
            update.setVisibility(View.GONE);
            preview2.setVisibility(View.GONE);
        }



//adapter for ITEMS
        String[] other = new String[]{"Lighting Outlet", "Convenience Outlet", "ACU", "Water Heater", "Range", "Refrigerator", "Spare"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.drop_down_item, other);
        autoCompleteTextView1.setAdapter(adapter1);

//adapter(dropdown) for Horse power
        String[] hp = new String[]{"1/6", "1/4", "1/3", "1/2", "3/4", "1", "1 1/2", "2", "3", "5", "7 1/2", "10"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.drop_down_item, hp);
        Horsepower.setAdapter(adapter2);

//adapter for type of pipes
        String[] pipe = new String[]{"EMT", "PVC", "IMC"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.drop_down_item, pipe);
        Typeofpipe.setAdapter(adapter3);

        //adapter(dropdown) for watt of lighting outlet
        String[] lo = new String[]{"100", "95", "90", "85", "80", "75", "70", "65", "60", "55", "50", "45", "40", "35", "30", "25", "20", "15", "10", "5"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.drop_down_item, lo);
        Wattslo.setAdapter(adapter4);

        // Define the maximum quantities for each wattage
        Map<String, Integer> maxQuantities = new HashMap<>();
        maxQuantities.put("100", 27);
        maxQuantities.put("95", 29);
        maxQuantities.put("90", 30);
        maxQuantities.put("85", 32);
        maxQuantities.put("80", 34);
        maxQuantities.put("75", 36);
        maxQuantities.put("70", 39);
        maxQuantities.put("65", 42);
        maxQuantities.put("60", 46);
        maxQuantities.put("55", 50);
        maxQuantities.put("50", 55);
        maxQuantities.put("45", 61);
        maxQuantities.put("40", 69);
        maxQuantities.put("35", 78);
        maxQuantities.put("30", 92);
        maxQuantities.put("25", 110);
        maxQuantities.put("20", 138);
        maxQuantities.put("15", 183);
        maxQuantities.put("10", 275);
        maxQuantities.put("5", 550);

        Wattslo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isAutoCompleteLOSelected = true;
                String selectedItem = Wattslo.getText().toString();
                if (maxQuantities.containsKey(selectedItem)) {
                    int maxQuantity = maxQuantities.get(selectedItem);
                    Watts.setText(selectedItem);
                    final int finalMaxQuantity = maxQuantity;
                    Quantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!s.toString().isEmpty()) {
                                int input = Integer.parseInt(s.toString());
                                if (input < 1 || input > finalMaxQuantity) {
                                    Quantity.setError("Quantity must be between 1 and " + finalMaxQuantity);
                                    next.setEnabled(false);
                                    update.setEnabled(false);
                                } else {
                                    Quantity.setError(null);
                                    next.setEnabled(true);
                                    update.setEnabled(true);
                                }
                            }
                        }
                    });
                } else {
                    Quantity.setError(null);
                    next.setEnabled(true);
                    update.setEnabled(true);
                    Quantity.setText(null);
                    Watts.setText(null);
                }
            }
        });




//selected item automated data
        Typeofpipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isAutoCompletePipeSelected = true;
                String selectedItem = Typeofpipe.getText().toString();
                if ("EMT".equals(selectedItem)) {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    CTYPE.setText("EMT");
                }
                if ("PVC".equals(selectedItem)) {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    CTYPE.setText("PVC");
                }
                if ("IMC".equals(selectedItem)) {
                    // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                    CTYPE.setText("IMC");
                }



            }
        });


        autoCompleteTextView1.setOnItemClickListener((adapterView, view, i, l) -> {
            isAutoCompleteItemSelected = true;
            isAutoCompleteLOSelected = true;
            String selectedItem = autoCompleteTextView1.getText().toString();

            switch (selectedItem) {
                case "Lighting Outlet":
                    AT.setText("15");
                    SMM.setText("3.5");
                    GMM.setText("3.5");
                    MMPlus.setText("20");
                    Wat.setVisibility(View.GONE);
                    Watlo.setVisibility(View.VISIBLE);
                    break;
                case "Convenience Outlet":
                    Wat.setVisibility(View.VISIBLE);
                    Watlo.setVisibility(View.GONE);
                    Watts.setText("180");
                    MMPlus.setText("20");
                    AT.setText("20");

                    break;
                case "Range":
                MMPlus.setText("20");
                AT.setText("30");
                    Quantity.setText("1");
                    break;
                default:
                    Wat.setVisibility(View.VISIBLE);
                    Watlo.setVisibility(View.GONE);
                    Quantity.setError(null);
                    next.setEnabled(true);
                    Quantity.setText("");
                    Watts.setText("");
                    Watts.setEnabled(true);

                    break;
            }

            // Set values based on selected item
            switch (selectedItem) {
                case "Range":
                    AT.setText("30");
                    break;

                case "ACU":

                    AT.setText("20");

                    break;
                case "Spare":
                    Quantity.setText("1");
                    AT.setText("20");
                    SMM.setText("Stub");
                    GMM.setText("");
                    MMPlus.setText("20");
                    break;
                case "Water Heater":
                    Wat.setVisibility(View.VISIBLE);
                    Watlo.setVisibility(View.GONE);
                    MMPlus.setText("20");
                    AT.setText("30");
                    // Limit Quantity input between 1 and 100
                    Quantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!s.toString().isEmpty()) {
                                int input = Integer.parseInt(s.toString());
                                if (input < 1 || input > 100) {
                                    Quantity.setError("Input must be between 1 and 100");
                                    next.setEnabled(false);
                                } else {
                                    Quantity.setError(null);
                                    next.setEnabled(true);
                                }
                            }
                        }
                    });
                    break;
                case "Refrigerator":
                    Wat.setVisibility(View.VISIBLE);
                    Watlo.setVisibility(View.GONE);

                    MMPlus.setText("20");
                    AT.setText("20");
                    // Limit Quantity input between 1 and 100
                    Quantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!s.toString().isEmpty()) {
                                int input = Integer.parseInt(s.toString());
                                if (input < 1 || input > 100) {
                                    Quantity.setError("Input must be between 1 and 100");
                                    next.setEnabled(false);
                                } else {
                                    Quantity.setError(null);
                                    next.setEnabled(true);
                                }
                            }
                        }
                    });
                    break;

                default:
                    break;
            }



            // Restrict input to numbers between 1 and 20 for Quantity if needed
            if ("Convenience Outlet".equals(selectedItem)) {
                Quantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!s.toString().isEmpty()) {
                            int input = Integer.parseInt(s.toString());
                            if (input < 1 || input > 20) {
                                Quantity.setError("Input must be between 1 and 20");
                                next.setEnabled(false);
                            } else {
                                Quantity.setError(null);
                                next.setEnabled(true);
                            }
                        }
                    }
                });
            }


            // Manage visibility of "horses" view
            horses.setVisibility("ACU".equals(selectedItem) ? View.VISIBLE : View.GONE);
            if ("ACU".equals(selectedItem)) Quantity.setText("1");

            // Set values for SNUM, GNUM, STYPE, and GTYPE based on selected item
            if (Arrays.asList("Lighting Outlet", "Convenience Outlet", "Water Heater", "Range", "ACU", "Refrigerator").contains(selectedItem)) {
                SNUM.setText("2");
                GNUM.setText("1");
                STYPE.setText("THHN");
                GTYPE.setText("THW");
            } else {
                SNUM.setText("");
                GNUM.setText("");
                STYPE.setText("UP");
                GTYPE.setText("");
            }


        });



//selected horsepower automated data
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
                        AT.setText("20");
                        MMPlus.setText("20");
                        break;
                    case "1/4":
                        Watts.setText("667");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("2.90");
                        VA.setText("667");
                        AT.setText("20");
                        MMPlus.setText("20");
                        break;
                    case "1/3":
                        Watts.setText("828");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("3.60");
                        VA.setText("828");
                        AT.setText("20");
                        MMPlus.setText("20");

                        break;
                    case "1/2":
                        Watts.setText("1127");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("4.90");
                        VA.setText("1127");
                        AT.setText("20");
                        MMPlus.setText("20");
                        break;
                    case "3/4":
                        Watts.setText("1587");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("6.90");
                        VA.setText("1587");
                        AT.setText("20");
                        MMPlus.setText("20");
                        break;
                    case "1":
                        Watts.setText("1840");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("8.00");
                        VA.setText("1840");
                        AT.setText("20");
                        MMPlus.setText("20");
                        break;
                    case "1 1/2":
                        Watts.setText("2300");
                        SMM.setText("3.5");
                        GMM.setText("3.5");
                        A.setText("10.00");
                        VA.setText("2300");
                        AT.setText("20");
                        MMPlus.setText("20");
                        break;
                    case "2":
                        Watts.setText("2760");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("12.00");
                        VA.setText("2760");
                        AT.setText("30");
                        MMPlus.setText("20");
                        break;
                    case "3":
                        Watts.setText("3910");
                        SMM.setText("5.5");
                        GMM.setText("5.5");
                        A.setText("17.00");
                        VA.setText("3910");
                        AT.setText("30");
                        MMPlus.setText("20");
                        break;
                    case "5":
                        Watts.setText("6440");
                        SMM.setText("8.0");
                        GMM.setText("5.5");
                        A.setText("28.00");
                        VA.setText("6440");
                        AT.setText("50");
                        MMPlus.setText("20");
                        break;
                    case "7 1/2":
                        Watts.setText("9220");
                        SMM.setText("22");
                        GMM.setText("8.0");
                        A.setText("40.00");
                        VA.setText("9220");
                        AT.setText("70");
                        MMPlus.setText("25");
                        break;
                    case "10":
                        Watts.setText("11500");
                        SMM.setText("30");
                        GMM.setText("8.0");
                        A.setText("50.00");
                        VA.setText("11500");
                        AT.setText("90");
                        MMPlus.setText("32");
                        break;
                    default:
                        // Default case if none of the above conditions match

                        break;

                }
            }
        });
        SharedPreferences finalSharedPreferences = sharedPreferences;
//selected item automated data
        Quantity.setEnabled(true);


//NEXT BUTTON
        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // Clear focus from any view that currently has it
                Watts.clearFocus();
                Quantity.clearFocus();
                others.clearFocus();
                Typeofpipe.clearFocus();
                Horsepower.clearFocus();



                if (Quantity.getText().toString().isEmpty()) {
                    // Clear focus from other fields
                    autoCompleteTextView1.clearFocus();
                    Watts.clearFocus();
                    Typeofpipe.clearFocus();

                    // Show AlertDialog if quantity field is empty
                    new AlertDialog.Builder(Inputing.this)
                            .setTitle("Alert")
                            .setMessage("Please fill Quantity")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Quantity.requestFocus(); // Set focus on Quantity field
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return; // Exit the method without proceeding further
                }

                if (horses.getVisibility() == View.VISIBLE && Horsepower.getText().toString().isEmpty()) {
                    // Clear focus from other fields
                    autoCompleteTextView1.clearFocus();
                    Watts.clearFocus();
                    Quantity.clearFocus();
                    Typeofpipe.clearFocus();

                    // Show AlertDialog if Horsepower field is visible and empty
                    new AlertDialog.Builder(Inputing.this)
                            .setTitle("Alert")
                            .setMessage("Please fill Horsepower")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Horsepower.requestFocus(); // Set focus on Horsepower field
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return; // Exit the method without proceeding further
                }


                if (autoCompleteTextView1.getText().toString().isEmpty()) {
                    // Clear focus from other fields
                    Quantity.clearFocus();
                    Watts.clearFocus();
                    Typeofpipe.clearFocus();

                    // Show AlertDialog if autoCompleteTextView1 field is empty
                    new AlertDialog.Builder(Inputing.this)
                            .setTitle("Alert")
                            .setMessage("Please fill the Item")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    autoCompleteTextView1.requestFocus(); // Set focus on autoCompleteTextView1 field
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return; // Exit the method without proceeding further
                }

                if (Watts.getText().toString().isEmpty()) {
                    // Clear focus from other fields
                    Quantity.clearFocus();
                    autoCompleteTextView1.clearFocus();
                    Typeofpipe.clearFocus();

                    // Show AlertDialog if watts field is empty
                    new AlertDialog.Builder(Inputing.this)
                            .setTitle("Alert")
                            .setMessage("Please fill Watts")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Watts.requestFocus(); // Set focus on Watts field
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return; // Exit the method without proceeding further
                }
                if (Typeofpipe.getText().toString().isEmpty()) {
                    // Clear focus from other fields
                    Quantity.clearFocus();
                    autoCompleteTextView1.clearFocus();
                    Watts.clearFocus();

                    // Show AlertDialog if type of pipe field is empty
                    new AlertDialog.Builder(Inputing.this)
                            .setTitle("Alert")
                            .setMessage("Please fill the type of pipe")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Typeofpipe.requestFocus(); // Set focus on Typeofpipe field
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return; // Exit the method without proceeding further
                }

// Check if all fields are empty
                if (Quantity.getText().toString().isEmpty() &&
                        autoCompleteTextView1.getText().toString().isEmpty() &&
                        Watts.getText().toString().isEmpty() &&
                        Typeofpipe.getText().toString().isEmpty()) {
                    // Clear focus from other fields
                    Quantity.clearFocus();
                    autoCompleteTextView1.clearFocus();
                    Watts.clearFocus();
                    Typeofpipe.clearFocus();

                    // Show AlertDialog if all fields are empty
                    new AlertDialog.Builder(Inputing.this)
                            .setTitle("Alert")
                            .setMessage("Fill up all the fields")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Quantity.requestFocus(); // Set focus on Quantity field
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {

                    // WHEN DATA IS OK PROCEED TO LOADSCHEDULE
                    //computation total

                    computeVA();
                    computeA();
                    String ProjectName = getIntent().getStringExtra("ProjectName");

                    //IF EMPTY ALERT
                    if (!isAutoCompleteItemSelected || Quantity.getText().toString().isEmpty() || Watts.getText().toString().isEmpty()) {
                        Toast.makeText(Inputing.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        // PROCEED
                        String selectedItem = autoCompleteTextView1.getText().toString();
                        String selectedWatts = Watts.getText().toString();
                        String add = others.getText().toString();
                        String selectedHP = Horsepower.getText().toString();

                        if ("Convenience Outlet".equals(selectedItem) || "Spare".equals(selectedItem) || "Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {

                            if (!add.isEmpty()) {
                                if (!selectedItem.isEmpty()) {
                                    selectedItem += "\n" + " (" + add + ")";
                                } else {
                                    selectedItem = add;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            }

                        } else if ("Lighting Outlet".equals(selectedItem)) {


                            if (!add.isEmpty()) {
                                if (!selectedWatts.isEmpty()) {
                                    selectedItem += ", " + selectedWatts + "W" + "\n" + " (" + add + ")";
                                } else {
                                    selectedItem = selectedWatts;
                                }

                                autoCompleteTextView1.setText(selectedItem);
                            } else if (!selectedWatts.isEmpty()) {
                                if (!selectedItem.isEmpty()) {
                                    selectedItem += ", " + selectedWatts + "W";
                                } else {
                                    selectedItem = selectedWatts;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            }

                        } else if ("ACU".equals(selectedItem)) {


                            if (!add.isEmpty()) {
                                if (!selectedHP.isEmpty()) {
                                    selectedItem += " " + selectedHP + "HP" + "\n" + " (" + add + ")";
                                } else {
                                    selectedItem = selectedHP;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            } else if (!selectedHP.isEmpty()) {
                                if (!selectedItem.isEmpty()) {
                                    selectedItem += " " + selectedHP + "HP";
                                } else {
                                    selectedItem = selectedHP;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            }

                        }




                        String counterText = CircuitNum.getText().toString();
                        String numberPart = counterText.replaceAll("\\D+", ""); // Remove all non-digit characters


// Convert the text to an integer value
                        int ctrValue = Integer.parseInt(numberPart);

// Check if the counter value is greater than or equal to 30
                        if (ctrValue >= 30) {
                            // Create an AlertDialog.Builder instance
                            AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);

                            // Set the dialog title and message
                            builder.setTitle("Alert");
                            builder.setMessage("You have reached the maximum limit of 30 circuits.");

                            // Add an OK button with a click listener that dismisses the dialog
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Dismiss the dialog
                                    dialog.dismiss();
                                    // Trigger the click event of the loadPreviewButton
                                    preview.performClick();
                                }
                            });

                            // Create and show the AlertDialog
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                        OPlus.setText("1");//MATIC
                        V.setText("230");//MATIC
                        P.setText("2");//MATIC
                        AF.setText("50");//MATIC
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
                    SharedPreferences.Editor editor = finalSharedPreferences.edit();
                    editor.apply();
                    Quantity.setText(null);
                    autoCompleteTextView1.setText(null);
                    Watts.setText(null);
                    Horsepower.setText(null);
                    others.setText(null);
                    Typeofpipe.setText(null);
                    Wattslo.setText(null);


                    Wat.setVisibility(View.VISIBLE);
                    Watlo .setVisibility(View.GONE);
                    horses.setVisibility(View.GONE);
                    counter();
                    intent.putExtra("ItemData", autoCompleteTextView1.getText().toString());
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBackAlert();
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String counterText = CircuitNum.getText().toString();
                String numberPart = counterText.replaceAll("\\D+", ""); // Remove all non-digit characters


// Convert the text to an integer value
                int ctrValue = Integer.parseInt(numberPart);
                // Check if the counter is less than 4
                if (ctrValue <= 2) {
                    // Create an AlertDialog.Builder instance
                    AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);

                    // Set the dialog title and message
                    builder.setTitle("Alert");
                    builder.setMessage("You need to add at least 2 items before previewing");

                    // Add an OK button with a click listener that dismisses the dialog
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    });


                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    if (ctrValue == 4 ||ctrValue == 6 || ctrValue == 8 || ctrValue == 10 || ctrValue == 12 || ctrValue == 14 || ctrValue == 16 || ctrValue == 18 || ctrValue == 20 || ctrValue == 22 || ctrValue == 24 || ctrValue == 26 || ctrValue == 28 ) {
                        // Create an AlertDialog.Builder instance




                        AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);
                        builder.setTitle("Alert");
                        builder.setMessage("You are trying to preview with an odd number. Do you want to continue and add 1 spare?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                showPercentSelectionDialogwithspare();



                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User canceled, do nothing
                            }
                        });
                        // Show the AlertDialog
                        builder.show();
                    } else {
                        if(ctrValue < 30) {
                            // First, prompt the user if they want to proceed
                            AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(Inputing.this);
                            confirmBuilder.setTitle("Alert");
                            confirmBuilder.setMessage("Do you want to proceed?");
                            confirmBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User wants to proceed, show the percent selection dialog
                                    showPercentSelectionDialog();
                                }
                            });
                            confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User canceled, do nothing
                                }
                            });
                            // Show the confirmation dialog
                            confirmBuilder.show();
                        }
                        else{
                            showPercentSelectionDialog();
                        }
                    }

// Method to show the percent selection dialog


// Method to handle the selected percent


                }
            }
        });

        preview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);
                builder.setTitle("Alert");
                builder.setMessage("Do you want to update this Item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Proceed with preview
                        // Clear focus from any view that currently has it

                        Watts.clearFocus();
                        Quantity.clearFocus();
                        others.clearFocus();
                        Typeofpipe.clearFocus();
                        Horsepower.clearFocus();


                        if (Quantity.getText().toString().isEmpty()) {
                            // Clear focus from other fields
                            autoCompleteTextView1.clearFocus();
                            Watts.clearFocus();
                            Typeofpipe.clearFocus();

                            // Show AlertDialog if quantity field is empty
                            new AlertDialog.Builder(Inputing.this)
                                    .setTitle("Alert")
                                    .setMessage("Please fill Quantity")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Quantity.requestFocus(); // Set focus on Quantity field
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return; // Exit the method without proceeding further
                        }

                        if (horses.getVisibility() == View.VISIBLE && Horsepower.getText().toString().isEmpty()) {
                            // Clear focus from other fields
                            autoCompleteTextView1.clearFocus();
                            Watts.clearFocus();
                            Quantity.clearFocus();
                            Typeofpipe.clearFocus();

                            // Show AlertDialog if Horsepower field is visible and empty
                            new AlertDialog.Builder(Inputing.this)
                                    .setTitle("Alert")
                                    .setMessage("Please fill Horsepower")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Horsepower.requestFocus(); // Set focus on Horsepower field
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return;
                        }


                        if (autoCompleteTextView1.getText().toString().isEmpty()) {
                            // Clear focus from other fields
                            Quantity.clearFocus();
                            Watts.clearFocus();
                            Typeofpipe.clearFocus();

                            // Show AlertDialog if autoCompleteTextView1 field is empty
                            new AlertDialog.Builder(Inputing.this)
                                    .setTitle("Alert")
                                    .setMessage("Please fill the Item")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            autoCompleteTextView1.requestFocus(); // Set focus on autoCompleteTextView1 field
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return; // Exit the method without proceeding further
                        }

                        if (Watts.getText().toString().isEmpty()) {
                            // Clear focus from other fields
                            Quantity.clearFocus();
                            autoCompleteTextView1.clearFocus();
                            Typeofpipe.clearFocus();

                            // Show AlertDialog if watts field is empty
                            new AlertDialog.Builder(Inputing.this)
                                    .setTitle("Alert")
                                    .setMessage("Please fill Watts")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Watts.requestFocus(); // Set focus on Watts field
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return; // Exit the method without proceeding further
                        }
                        if (Typeofpipe.getText().toString().isEmpty()) {
                            // Clear focus from other fields
                            Quantity.clearFocus();
                            autoCompleteTextView1.clearFocus();
                            Watts.clearFocus();

                            // Show AlertDialog if type of pipe field is empty
                            new AlertDialog.Builder(Inputing.this)
                                    .setTitle("Alert")
                                    .setMessage("Please fill the type of pipe")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Typeofpipe.requestFocus(); // Set focus on Typeofpipe field
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return; // Exit the method without proceeding further
                        }

// Check if all fields are empty
                        if (Quantity.getText().toString().isEmpty() &&
                                autoCompleteTextView1.getText().toString().isEmpty() &&
                                Watts.getText().toString().isEmpty() &&
                                Typeofpipe.getText().toString().isEmpty()) {
                            // Clear focus from other fields
                            Quantity.clearFocus();
                            autoCompleteTextView1.clearFocus();
                            Watts.clearFocus();
                            Typeofpipe.clearFocus();

                            // Show AlertDialog if all fields are empty
                            new AlertDialog.Builder(Inputing.this)
                                    .setTitle("Alert")
                                    .setMessage("Fill up all the fields")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Quantity.requestFocus(); // Set focus on Quantity field
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        } else {
                            String ProjectName = getIntent().getStringExtra("ProjectName");
                            // PROCEED
                            String selectedItem = autoCompleteTextView1.getText().toString();
                            String selectedWatts = Watts.getText().toString();
                            if ("Water Heater".equals(selectedItem) || "Range".equals(selectedItem)) {
                                // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                                AT.setText("30");
                            }
                            if ("Convenience Outlet".equals(selectedItem) || "ACU".equals(selectedItem) || "Spare".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {
                                // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                                AT.setText("20"); // Set the default value for other items
                            }
                            if ("Convenience Outlet".equals(selectedItem) || "Refrigerator".equals(selectedItem) || "ACU".equals(selectedItem)) {
                                // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                                SMM.setText("3.5");
                                GMM.setText("3.5");
                            }
                            if ("Water Heater".equals(selectedItem) || "Range".equals(selectedItem)) {
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
                            if ("Lighting Outlet".equals(selectedItem) || "Convenience Outlet".equals(selectedItem) || "Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "Spare".equals(selectedItem) || "Refrigerator".equals(selectedItem)){
                                MMPlus.setText("20");
                            }else{
                                MMPlus.setText("");
                            }

                            if ("Lighting Outlet".equals(selectedItem) || "Convenience Outlet".equals(selectedItem) || "Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "ACU".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {
                                // If the user chooses Water Heater or Range or Refrigerator, set the value of AT to 30
                                SNUM.setText("2");//MATIC
                                GNUM.setText("1");//MATIC
                                STYPE.setText("THHN");//MATIC
                                GTYPE.setText("THW");//MATIC
                            } else {
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


                            String add = others.getText().toString();
                            String selectedHP = Horsepower.getText().toString();
                            switch (selectedHP) {
                                case "1/6":
                                    Watts.setText("506");
                                    SMM.setText("3.5");
                                    GMM.setText("3.5");
                                    A.setText("2.20");
                                    VA.setText("506");
                                    AT.setText("20");
                                    MMPlus.setText("20");
                                    break;
                                case "1/4":
                                    Watts.setText("667");
                                    SMM.setText("3.5");
                                    GMM.setText("3.5");
                                    A.setText("2.90");
                                    VA.setText("667");
                                    AT.setText("20");
                                    MMPlus.setText("20");
                                    break;
                                case "1/3":
                                    Watts.setText("828");
                                    SMM.setText("3.5");
                                    GMM.setText("3.5");
                                    A.setText("3.60");
                                    VA.setText("828");
                                    AT.setText("20");
                                    MMPlus.setText("20");

                                    break;
                                case "1/2":
                                    Watts.setText("1127");
                                    SMM.setText("3.5");
                                    GMM.setText("3.5");
                                    A.setText("4.90");
                                    VA.setText("1127");
                                    AT.setText("20");
                                    MMPlus.setText("20");
                                    break;
                                case "3/4":
                                    Watts.setText("1587");
                                    SMM.setText("3.5");
                                    GMM.setText("3.5");
                                    A.setText("6.90");
                                    VA.setText("1587");
                                    AT.setText("20");
                                    MMPlus.setText("20");
                                    break;
                                case "1":
                                    Watts.setText("1840");
                                    SMM.setText("3.5");
                                    GMM.setText("3.5");
                                    A.setText("8.00");
                                    VA.setText("1840");
                                    AT.setText("20");
                                    MMPlus.setText("20");
                                    break;
                                case "1 1/2":
                                    Watts.setText("2300");
                                    SMM.setText("3.5");
                                    GMM.setText("3.5");
                                    A.setText("10.00");
                                    VA.setText("2300");
                                    AT.setText("20");
                                    MMPlus.setText("20");
                                    break;
                                case "2":
                                    Watts.setText("2760");
                                    SMM.setText("5.5");
                                    GMM.setText("5.5");
                                    A.setText("12.00");
                                    VA.setText("2760");
                                    AT.setText("30");
                                    MMPlus.setText("20");
                                    break;
                                case "3":
                                    Watts.setText("3910");
                                    SMM.setText("5.5");
                                    GMM.setText("5.5");
                                    A.setText("17.00");
                                    VA.setText("3910");
                                    AT.setText("30");
                                    MMPlus.setText("20");
                                    break;
                                case "5":
                                    Watts.setText("6440");
                                    SMM.setText("8.0");
                                    GMM.setText("5.5");
                                    A.setText("28.00");
                                    VA.setText("6440");
                                    AT.setText("50");
                                    MMPlus.setText("20");
                                    break;
                                case "7 1/2":
                                    Watts.setText("9220");
                                    SMM.setText("22");
                                    GMM.setText("8.0");
                                    A.setText("40.00");
                                    VA.setText("9220");
                                    AT.setText("70");
                                    MMPlus.setText("25");
                                    break;
                                case "10":
                                    Watts.setText("11500");
                                    SMM.setText("30");
                                    GMM.setText("8.0");
                                    A.setText("50.00");
                                    VA.setText("11500");
                                    AT.setText("90");
                                    MMPlus.setText("32");
                                    break;
                                default:
                                    // Default case if none of the above conditions match

                                    break;

                            }

                            if ("Convenience Outlet".equals(selectedItem) || "Spare".equals(selectedItem) || "Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {

                                if (!add.isEmpty()) {
                                    if (!selectedItem.isEmpty()) {
                                        selectedItem += "\n" + " (" + add + ")";
                                    } else {
                                        selectedItem = add;
                                    }
                                    autoCompleteTextView1.setText(selectedItem);
                                }

                            } else if ("Lighting Outlet".equals(selectedItem)) {







                                if (!add.isEmpty()) {
                                    if (!selectedWatts.isEmpty()) {
                                        selectedItem += ", " + selectedWatts + "W" + "\n" + " (" + add + ")";
                                    } else {
                                        selectedItem = selectedWatts;
                                    }

                                    autoCompleteTextView1.setText(selectedItem);
                                } else if (!selectedWatts.isEmpty()) {
                                    if (!selectedItem.isEmpty()) {
                                        selectedItem += ", " + selectedWatts + "W";
                                    } else {
                                        selectedItem = selectedWatts;
                                    }
                                    autoCompleteTextView1.setText(selectedItem);
                                }

                            } else if ("ACU".equals(selectedItem)) {


                                if (!add.isEmpty()) {
                                    if (!selectedHP.isEmpty()) {
                                        selectedItem += " " + selectedHP + "HP" + "\n" + " (" + add + ")";
                                    } else {
                                        selectedItem = selectedHP;
                                    }
                                    autoCompleteTextView1.setText(selectedItem);
                                } else if (!selectedHP.isEmpty()) {
                                    if (!selectedItem.isEmpty()) {
                                        selectedItem += " " + selectedHP + "HP";
                                    } else {
                                        selectedItem = selectedHP;
                                    }
                                    autoCompleteTextView1.setText(selectedItem);
                                }

                            }

                            computeVA();
                            computeA();

                            OPlus.setText("1");
                            V.setText("233");
                            P.setText("2");
                            AF.setText("50");

                            helper.updateData(projectTable,
                                    ProjectName,
                                    Quantity.getText().toString()
                                    , autoCompleteTextView1.getText().toString()
                                    , OPlus.getText().toString()
                                    , V.getText().toString()
                                    , VA.getText().toString()
                                    , A.getText().toString()
                                    , P.getText().toString()
                                    , AT.getText().toString()
                                    , AF.getText().toString()
                                    , SNUM.getText().toString()
                                    , SMM.getText().toString()
                                    , STYPE.getText().toString()
                                    , GNUM.getText().toString()
                                    , GMM.getText().toString()
                                    , GTYPE.getText().toString()
                                    , MMPlus.getText().toString()
                                    , CTYPE.getText().toString()
                            );
                            Toast.makeText(Inputing.this, "Data Updated", Toast.LENGTH_SHORT).show();


                        }

                        proceedWithPreview1();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled, do nothing
                    }
                });
                // Show the AlertDialog
                builder.show();


            }


        });


        imageView = findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


    }

    //demand factor

    private void showPercentSelectionDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_input_percent, null);
        final EditText loadNameEditText = dialogView.findViewById(R.id.editTextLoadname);
        final EditText percentEditText = dialogView.findViewById(R.id.editTextPercent);
        final AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.autoCompletepipe);

        AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);
        builder.setTitle("Please fill this out.");
        builder.setView(dialogView);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            updatePositiveButtonState(positiveButton, percentEditText, autoCompleteTextView);

            positiveButton.setOnClickListener(view -> {
                String inputText = percentEditText.getText().toString().trim();
                String autoCompleteText = autoCompleteTextView.getText().toString().trim();
                String loadName1 = loadNameEditText.getText().toString().trim();

                if (!inputText.isEmpty() && !autoCompleteText.isEmpty() && !loadName1.isEmpty()) {
                    try {
                        int inputPercent = Integer.parseInt(inputText);
                        float multipliedPercent = (float) inputPercent / 100.0f;
                        String demandText = String.format("%.2f", multipliedPercent);
                        TextView demand = findViewById(R.id.demand);


                        demand.setText(demandText);

                        String selectedPipe = autoCompleteText;
                        Mainpipetxt.setText(selectedPipe);
                        loadname.setText(loadName1); // Save the input to nameLS
                        proceedWithPreview();
                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        Toast.makeText(Inputing.this, "Invalid input for percent", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Inputing.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        String[] mainPipeOptions = {"EMT", "PVC", "IMC"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Inputing.this, android.R.layout.simple_dropdown_item_1line, mainPipeOptions);
        autoCompleteTextView.setAdapter(adapter);

        percentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updatePositiveButtonState(dialog.getButton(AlertDialog.BUTTON_POSITIVE), percentEditText, autoCompleteTextView);
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!TextUtils.isEmpty(autoCompleteTextView.getText().toString()));
            }
        });

        dialog.show();
    }

    private void updatePositiveButtonState(Button positiveButton, EditText percentEditText, AutoCompleteTextView autoCompleteTextView) {
        String inputText = percentEditText.getText().toString().trim();
        boolean percentValid = !inputText.isEmpty();
        if (percentValid) {
            try {
                int inputPercent = Integer.parseInt(inputText);
                percentValid = inputPercent >= 1 && inputPercent <= 100;
            } catch (NumberFormatException e) {
                percentValid = false;
            }
        }
        positiveButton.setEnabled(percentValid && !TextUtils.isEmpty(autoCompleteTextView.getText().toString()));
    }


    private void showPercentSelectionDialogwithspare() {

        // Inflate the dialog layout XML
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_input_percent, null);
        final EditText inputEditText = dialogView.findViewById(R.id.editTextPercent);
        final EditText loadNameEditText = dialogView.findViewById(R.id.editTextLoadname);

        final AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.autoCompletepipe);


        AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);
        builder.setTitle("Please fill this out.");
        builder.setView(dialogView);

        // Disable the positive button initially
        builder.setPositiveButton("OK", null);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Create the dialog
        dialog = builder.create();

        // Set up a listener to enable/disable the positive button based on input validity
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputText = s.toString().trim();
                if (!inputText.isEmpty()) {
                    int inputPercent = Integer.parseInt(inputText);
                    // Enable the OK button if input is valid
                    // Disable the OK button if input is invalid
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(inputPercent >= 1 && inputPercent <= 100);
                } else {
                    // Disable the OK button if input is empty
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });

        // Set click listener for the positive button
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if (inputEditText.getText().toString().trim().isEmpty()) {
                    // If input is empty, disable the OK button
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String ProjectName = getIntent().getStringExtra("ProjectName");
                        // Proceed with preview
                        Quantity.setText("1");
                        autoCompleteTextView1.setText("Spare");
                        OPlus.setText("1");//MATIC
                        V.setText("230");//MATIC
                        Watts.setText("1500");
                        P.setText("2");//MATIC
                        AT.setText("20");
                        AF.setText("50");//MATIC
                        SNUM.setText("");//MATIC
                        SMM.setText("Stub");
                        STYPE.setText("UP");//MATIC
                        GNUM.setText("");//MATIC
                        GMM.setText("");//MATIC
                        GTYPE.setText("");//MATIC
                        MMPlus.setText("20");//MATIC
                        CTYPE.setText("PVC");//MATIC

                        computeVA();
                        computeA();

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
                        Quantity.setText(null);
                        autoCompleteTextView1.setText(null);
                        Watts.setText(null);
                        Horsepower.setText(null);
                        Typeofpipe.setText(null);
                        others.setText(null);

                        // Handle click on OK button
                        String inputText = inputEditText.getText().toString().trim();
                        int inputPercent = Integer.parseInt(inputText);
                        String loadName1 = loadNameEditText.getText().toString().trim();
                        float multipliedPercent = (float) inputPercent / 100.0f;
                        String demandText = String.format("%.2f", multipliedPercent);
                        TextView demand = findViewById(R.id.demand);
                        demand.setText(demandText);
                        loadname.setText(loadName1);
                        String selectedPipe = autoCompleteTextView.getText().toString().trim();
                        Mainpipetxt.setText(selectedPipe);
                        proceedWithPreview();
                        counter();

                        dialog.dismiss();
                    }
                });
            }
        });
        // Set up AutoCompleteTextView with options for selecting type of conduit
        String[] mainPipeOptions = {"EMT", "PVC", "IMC"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Inputing.this, android.R.layout.simple_dropdown_item_1line, mainPipeOptions);
        autoCompleteTextView.setAdapter(adapter);

        // Set up a listener for the AutoCompleteTextView to enable/disable the OK button based on selection
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!TextUtils.isEmpty(autoCompleteTextView.getText().toString()));
            }
        });

        // Show the dialog
        dialog.show();
    }

    private void proceedWithPreview1() {

        double highestAmp = findHighestAmp();
        HighestAmp12.setText(String.valueOf(highestAmp));
        String passVA = TotalVA.getText().toString();
        String passA = TotalA.getText().toString();
        String passHIGHEST = HighestAmp12.getText().toString();
        Intent intent = new Intent(getApplicationContext(), Loadschedule.class);
        intent.putExtra("TOTALVA", passVA);
        intent.putExtra("TOTALA", passA);
        intent.putExtra("HIGHA", passHIGHEST);
        startActivity(intent);

    }

    private void proceedWithPreview() {

        double highestAmp = findHighestAmp();
        HighestAmp12.setText(String.valueOf(highestAmp));
        String DEMAND = demand.getText().toString();
        String passVA = TotalVA.getText().toString();
        String loadnamesave= loadname.getText().toString();
        String passA = TotalA.getText().toString();
        String passHIGHEST = HighestAmp12.getText().toString();
        String mainpipo = Mainpipetxt.getText().toString();
        Intent intent = new Intent(getApplicationContext(), Loadschedule.class);
        intent.putExtra("loadnamesave", loadnamesave);
        intent.putExtra("TOTALVA", passVA);
        intent.putExtra("TOTALA", passA);
        intent.putExtra("HIGHA", passHIGHEST);
        intent.putExtra("DEMAND", DEMAND);
        intent.putExtra("mainpipo", mainpipo);
        startActivity(intent);

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
    private void showBackAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Back")
                .setMessage("Are you sure you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the back action

                        finish(); // Close the activity or any other action you want
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    public void onBackPressedtrue() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Close the activity
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to proceed?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        proceedToMenuActivity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, user canceled the action
                    }
                })
                .show();
    }


    private void proceedToMenuActivity() {
        Intent intent = new Intent(Inputing.this, Menu.class);
        startActivity(intent);

    }

    private void counter(){
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getAllItemsAndStartNextActivity(Inputing.this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String> itemSList) {

                if (itemSList == null || itemSList.isEmpty()) {
                    CircuitNum.setText("CIRCUIT NO. 1");
                } else {

                    switch (itemSList.size()) {


                        case 1:
                            CircuitNum.setText("CIRCUIT NO. 2");
                            break;
                        case 2:
                            CircuitNum.setText("CIRCUIT NO. 3");
                            break;
                        case 3:
                            CircuitNum.setText("CIRCUIT NO. 4");
                            break;
                        case 4:

                            CircuitNum.setText("CIRCUIT NO. 5");
                            break;
                        case 5:

                            CircuitNum.setText("CIRCUIT NO. 6");
                            break;
                        case 6:

                            CircuitNum.setText("CIRCUIT NO. 7");
                            break;
                        case 7:

                            CircuitNum.setText("CIRCUIT NO. 8");
                            break;
                        case 8:

                            CircuitNum.setText("CIRCUIT NO. 9");
                            break;
                        case 9:

                            CircuitNum.setText("CIRCUIT NO. 10");
                            break;
                        case 10:

                            CircuitNum.setText("CIRCUIT NO. 11");
                            break;
                        case 11:

                            CircuitNum.setText("CIRCUIT NO. 12");
                            break;
                        case 12:

                            CircuitNum.setText("CIRCUIT NO. 13");
                            break;
                        case 13:

                            CircuitNum.setText("CIRCUIT NO. 14");
                            break;
                        case 14:

                            CircuitNum.setText("CIRCUIT NO. 15");
                            break;
                        case 15:

                            CircuitNum.setText("CIRCUIT NO. 16");
                            break;
                        case 16:

                            CircuitNum.setText("CIRCUIT NO. 17");
                            break;
                        case 17:

                            CircuitNum.setText("CIRCUIT NO. 18");
                            break;
                        case 18:

                            CircuitNum.setText("CIRCUIT NO. 19");
                            break;
                        case 19:

                            CircuitNum.setText("CIRCUIT NO. 20");
                            break;
                        case 20:

                            CircuitNum.setText("CIRCUIT NO. 21");
                            break;
                        case 21:

                            CircuitNum.setText("CIRCUIT NO. 22");
                            break;

                        case 22:

                            CircuitNum.setText("CIRCUIT NO. 23");
                            break;
                        case 23:

                            CircuitNum.setText("CIRCUIT NO. 24");
                            break;
                        case 24:

                            CircuitNum.setText("CIRCUIT NO. 25");
                            break;
                        case 25:

                            CircuitNum.setText("CIRCUIT NO. 26");
                            break;
                        case 26:

                            CircuitNum.setText("CIRCUIT NO. 27");
                            break;
                        case 27:

                            CircuitNum.setText("CIRCUIT NO. 28");
                            break;
                        case 28:

                            CircuitNum.setText("CIRCUIT NO. 29");
                            break;
                        case 29:

                            CircuitNum.setText("CIRCUIT NO. 30");
                            break;


                        case 30:
                            CircuitNum.setText("CIRCUIT NO. 30");
                            break;
                        default:
                            // Handle other cases if necessary
                    }
                }
            }
        });
    }

}