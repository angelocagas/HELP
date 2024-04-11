package com.example.projectone;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.projectone.Databases.ProjectTable;
import com.google.firebase.auth.FirebaseAuth;
import com.example.projectone.Helper.DatabaseHelper;

public class Home extends Fragment {

    private DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button create = view.findViewById(R.id.create);
        Button about = view.findViewById(R.id.about);
        databaseHelper = new DatabaseHelper(getActivity());

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_choose_main_pipe, null);
                final AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.autoCompletepipe);

                // Set up AutoCompleteTextView with options
                String[] mainPipeOptions = {"EMT", "PVC", "IMC"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mainPipeOptions);
                autoCompleteTextView.setAdapter(adapter);

                builder.setView(dialogView)
                        .setTitle("Select type of Conduit")
                        .setPositiveButton("OK", null) // Initially set to null
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing if cancelled
                            }
                        });
                final AlertDialog dialog = builder.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setEnabled(false); // Initially disable the OK button

                        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}

                            @Override
                            public void afterTextChanged(Editable s) {
                                positiveButton.setEnabled(!TextUtils.isEmpty(autoCompleteTextView.getText().toString()));
                            }
                        });

                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String selectedMainPipe = autoCompleteTextView.getText().toString();

                                // Access the SharedPreferences file and remove the currentTableCount value
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                                editor.clear(); // Removes all data from SharedPreferences
                                editor.apply();

                                // Start Inputing activity
                                Intent intent = new Intent(getActivity(), Inputing.class);
                                intent.putExtra("mainPipe", selectedMainPipe);
                                startActivity(intent);
                                databaseHelper.clearTable();

                                dialog.dismiss();
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the AboutActivity
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}
