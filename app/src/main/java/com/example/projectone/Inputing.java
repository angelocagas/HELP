package com.example.projectone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import java.util.List;

public class Inputing extends AppCompatActivity {
    // Declare ArrayList to store "A" values
    ArrayList<Double> arrayAmp = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView1, Horsepower;
    TextInputLayout horses;
    TextView Counter2, HighestAmp12, CNM,TotalVA, TotalA, others, CircuitNum, OPlus, V, VA, A, P, AT, AF, SNUM, SMM, STYPE, GNUM, GMM, GTYPE, MMPlus, CTYPE;
    Button next, preview, preview2, back, update;
    TextInputEditText Quantity, Watts, Others;
    DatabaseHelper helper;
    private boolean isAutoCompleteItemSelected = false;
    int counter = 1;

    private SharedPreferences sharedPreferences;
    private DecimalFormat decimalFormat;
    double totalVAValue = 0.00;
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
        counter = 1; // reset yung sa counter dun sa input display


        setContentView(R.layout.activity_inputing);
        others = findViewById(R.id.others);
        helper = DatabaseHelper.getInstance(this);
        CircuitNum = findViewById(R.id.CircuitNum);
        autoCompleteTextView1 = findViewById(R.id.Items);
        horses = findViewById(R.id.horses);
        next = findViewById(R.id.next);
        preview = findViewById(R.id.preview);
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
        Horsepower = findViewById(R.id.horse);
        update = findViewById(R.id.update);
        TotalA = findViewById(R.id.TotalA);
        TotalVA = findViewById(R.id.TotalVA);
        CNM = findViewById(R.id.CNM);
        HighestAmp12 = findViewById(R.id.HighestAmp);
        Counter2 = findViewById(R.id.counter2);

        Intent intent = getIntent();
        String Cirnum = intent.getStringExtra("CNM");
        CNM.setText(Cirnum);

        try {
            cirnum = Integer.parseInt(CNM.getText().toString().trim());

        } catch (NumberFormatException e) {

        }

        // Check if editing mode is enabled
        boolean isEditMode = getIntent().getBooleanExtra("EditMode", false);
        if (isEditMode) {
            //populate the value in textview
            projectTable = (ProjectTable)getIntent().getSerializableExtra("ProjectTable");
            assert projectTable != null;

            Quantity.setText(projectTable.getQuantity());
            String quan = projectTable.getQuantity();
            String item = projectTable.getItem();
            String  VAs = projectTable.getVA();


            // If editing mode is enabled, disable the "Next" button
            preview.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            CircuitNum.setText("Update");
            preview2.setVisibility(View.VISIBLE);
            update.setVisibility(View.VISIBLE);



            // Check if item starts with "LIGHTING OUTLET"
            if (item.startsWith("Lighting Outlet")) {
                autoCompleteTextView1.setText("Lighting Outlet");
            }
            // Check if item starts with "ACU"
            else if (item.startsWith("ACU")) {
                autoCompleteTextView1.setText("ACU");
                horses.setVisibility(View.VISIBLE);
                //for HORSEPOWER DISPLAY
                int startIndex = item.indexOf("ACU ");
                int endIndex = item.indexOf("PH");
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    // Extract the interval
                    String interval = item.substring(startIndex + 1, endIndex).trim();

                    // Display the interval in horsepower
                    Horsepower.setText(interval);

                }

            } else if (item.startsWith("Convenience Outlet")) {
                autoCompleteTextView1.setText("Convenience Outlet");
            } else if (item.startsWith("Water Heater")) {
                autoCompleteTextView1.setText("Water Heater");
            } else if (item.startsWith("Range")) {
                autoCompleteTextView1.setText("Range");
            } else if (item.startsWith("Refrigerator")) {
                autoCompleteTextView1.setText("Refrigerator");
            }else if (item.startsWith("Spare")) {
                autoCompleteTextView1.setText("Spare");
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
                int wattsup = vas / quantity ;
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
        String[] other = new String[]{"Lighting Outlet", "Convenience Outlet", "ACU", "Water Heater", "Range", "Refrigerator","Spare"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.drop_down_item, other);
        autoCompleteTextView1.setAdapter(adapter1);

//adapter(dropdown) for Horse power
        String[] hp = new String[]{"1/6", "1/4", "1/3", "0.5", "0.75", "1", "1.5", "2", "3", "5", "7.5", "10"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.drop_down_item, hp);
        Horsepower.setAdapter(adapter2);





//selected item automated data
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
        SharedPreferences finalSharedPreferences = sharedPreferences;

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


                // Check if any of the fields are empty
                if (Quantity.getText().toString().isEmpty() || autoCompleteTextView1.getText().toString().isEmpty() || Watts.getText().toString().isEmpty()) {
                    // Show AlertDialog if any field is empty
                    new AlertDialog.Builder(Inputing.this)
                            .setTitle("Alert")
                            .setMessage("Please fill all the fields")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return; // Exit the method without proceeding further
                } else {

                    // WHEN DATA IS OK PROCEED TO LOADSCHEDULE
                    //computation total

                    computeVA();
                    computeA();
                    counter++;
                    CircuitNum.setText("CIRCUIT NO. " + counter);
                    Counter2.setText(String.valueOf(counter));
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

                        if ("Convenience Outlet".equals(selectedItem) ||"Spare".equals(selectedItem) ||"Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {

                            if (!add.isEmpty()) {
                                if (!selectedItem.isEmpty()) {
                                    selectedItem += "\n" + " (" + add + ")";
                                } else {
                                    selectedItem = add;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            }

                        } else if ("Lighting Outlet".equals(selectedItem) ) {


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

                        }else if ("ACU".equals(selectedItem) ) {


                            if (!add.isEmpty()) {
                                if (!selectedHP.isEmpty()) {
                                    selectedItem += " " + selectedHP + "HP" + "\n" + " (" + add + ")";
                                } else {
                                    selectedItem = selectedHP;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            }else if (!selectedHP.isEmpty()) {
                                if (!selectedItem.isEmpty()) {
                                    selectedItem += " " + selectedHP + "HP";
                                } else {
                                    selectedItem = selectedHP;
                                }
                                autoCompleteTextView1.setText(selectedItem);
                            }

                        }




                        // counter para sa skel
                        String counterText = Counter2.getText().toString();

// Convert the text to an integer value
                        int ctrValue = Integer.parseInt(counterText);

// Check if the counter value is greater than or equal to 30
                        if (ctrValue >= 31) {
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
                    SharedPreferences.Editor editor = finalSharedPreferences.edit();
                    editor.putInt("counter", counter);
                    editor.apply();
                    Quantity.setText(null);
                    autoCompleteTextView1.setText(null);
                    Watts.setText(null);
                    Horsepower.setText(null);
                    others.setText(null);
                    horses.setVisibility(View.GONE);

                    intent.putExtra("ItemData", autoCompleteTextView1.getText().toString());
                }
            } });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Call onBackPressed() method when the button is clicked
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                // Check if the counter is less than 4
                if (counter <= 4) {
                    // Create an AlertDialog.Builder instance
                    AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);

                    // Set the dialog title and message
                    builder.setTitle("Alert");
                    builder.setMessage("You need to add at least 4 items before previewing");

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

                    if  (counter == 6 || counter == 8 || counter == 10 || counter == 12 || counter == 14 || counter == 16 || counter == 18 || counter == 20 || counter == 22 || counter == 24 || counter == 26 || counter == 28 || counter == 30) {
                        // Create an AlertDialog.Builder instance
                        String ProjectName = getIntent().getStringExtra("ProjectName");

                        AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);
                        builder.setTitle("Alert");
                        builder.setMessage("You are trying to preview with an odd number. Do you want to continue and add 1 spare?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                counter++;
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
                                GTYPE.setText("THW");//MATIC
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
                                others.setText(null);

                                proceedWithPreview();

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


                        AlertDialog.Builder builder = new AlertDialog.Builder(Inputing.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Do you want to proceed?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Proceed with preview
                                proceedWithPreview();
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




                }
            } });

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
                        if(Quantity.getText().toString().isEmpty() || autoCompleteTextView1.getText().toString().isEmpty() || Watts.getText().toString().isEmpty())
                        {
                            Toast.makeText(Inputing.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String ProjectName = getIntent().getStringExtra("ProjectName");
                            // PROCEED
                            String selectedItem = autoCompleteTextView1.getText().toString();
                            String selectedWatts = Watts.getText().toString();
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

                            if ("Lighting Outlet".equals(selectedItem) || "Convenience Outlet".equals(selectedItem) ||"Water Heater".equals(selectedItem) || "Spare".equals(selectedItem) || "Range".equals(selectedItem) || "ACU".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {

                            }


                            if ("ACU".equals(selectedItem)) {
                                horses.setVisibility(View.VISIBLE);
                                Quantity.setText("1");
                            } else {
                                horses.setVisibility(View.GONE);
                            }

                            computeVA();
                            computeA();

                            String add = others.getText().toString();
                            String selectedHP = Horsepower.getText().toString();

                            if ("Convenience Outlet".equals(selectedItem) ||"Spare".equals(selectedItem) ||"Water Heater".equals(selectedItem) || "Range".equals(selectedItem) || "Refrigerator".equals(selectedItem)) {

                                if (!add.isEmpty()) {
                                    if (!selectedItem.isEmpty()) {
                                        selectedItem += "\n" + " (" + add + ")";
                                    } else {
                                        selectedItem = add;
                                    }
                                    autoCompleteTextView1.setText(selectedItem);
                                }

                            } else if ("Lighting Outlet".equals(selectedItem) ) {


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

                            }else if ("ACU".equals(selectedItem) ) {


                                if (!add.isEmpty()) {
                                    if (!selectedHP.isEmpty()) {
                                        selectedItem += " " + selectedHP + "HP" + "\n" + " (" + add + ")";
                                    } else {
                                        selectedItem = selectedHP;
                                    }
                                    autoCompleteTextView1.setText(selectedItem);
                                }else if (!selectedHP.isEmpty()) {
                                    if (!selectedItem.isEmpty()) {
                                        selectedItem += " " + selectedHP + "HP";
                                    } else {
                                        selectedItem = selectedHP;
                                    }
                                    autoCompleteTextView1.setText(selectedItem);
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
                                    ,CTYPE.getText().toString());
                            Toast.makeText(Inputing.this, "Data Updated", Toast.LENGTH_SHORT).show();


                        }

                        proceedWithPreview();
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




    private void proceedWithPreview() {

        double highestAmp = findHighestAmp();
        HighestAmp12.setText(String.valueOf(highestAmp));

        // Once the highest values are determined, create an intent to start the new activity
        String passVA = TotalVA.getText().toString();
        String passA = TotalA.getText().toString();
        String passHIGHEST = HighestAmp12.getText().toString();
        String skel = Counter2.getText().toString();
        Intent intent = new Intent(getApplicationContext(), Loadschedule.class);


        // Pass the necessary data to the loadsched through the intent
        intent.putExtra("TOTALVA", passVA);
        intent.putExtra("TOTALA", passA);
        intent.putExtra("HIGHA", passHIGHEST);
        intent.putExtra("CTR", skel);
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

}
