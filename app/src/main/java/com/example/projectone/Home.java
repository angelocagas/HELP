package com.example.projectone;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.example.projectone.Helper.DatabaseHelper;

public class Home extends Fragment {

    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button create = view.findViewById(R.id.create);
        databaseHelper = new DatabaseHelper(getActivity());

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the table before starting the Inputing activity

                databaseHelper.clearTable();
                // Access the SharedPreferences file and remove the currentTableCount value
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                editor.clear(); // Removes all data from SharedPreferences
                editor.apply();


                startActivity(new Intent(getActivity(), Inputing.class));
            }
        });

        return view;
    }
}
