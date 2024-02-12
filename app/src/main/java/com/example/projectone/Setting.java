package com.example.projectone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Setting extends Fragment {

    private TextView nameTextView, emailTextView, contactTextView, userIdTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Initialize views
        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        contactTextView = view.findViewById(R.id.contact);
        userIdTextView = view.findViewById(R.id.userId); // Assuming you have a TextView for user ID

        Button logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        // Retrieve data from Firestore
        retrieveUserData();

        return view;
    }

    private void retrieveUserData() {
        // Get Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get current authenticated user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Retrieve user ID
            String userId = currentUser.getUid();

            // Set user ID in TextView
            userIdTextView.setText(userId);

            // Reference to the document using user ID
            DocumentReference documentReference = db.collection("users")
                    .document(userId);

            // Attach a listener to receive the data
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        // Handle errors
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        // Document exists, retrieve data
                        String name = documentSnapshot.getString("Fullname");
                        String email = documentSnapshot.getString("Email");
                        String contact = documentSnapshot.getString("Contact");

                        // Update UI with retrieved data
                        updateUI(name, email, contact);
                    }
                }
            });
        }
    }

    private void updateUI(String name, String email, String contact) {
        // Update TextViews with retrieved data
        nameTextView.setText(name);
        emailTextView.setText(email);
        contactTextView.setText(contact);
    }
}
