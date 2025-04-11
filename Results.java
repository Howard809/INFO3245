package com.example.newproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Results extends AppCompatActivity {

    TextView txtTitle, txtName, txtLName, txtDate, txtServ, txtBtests;
    Button btnS1, btnS2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtTitle = findViewById(R.id.txtTitle);
        txtName = findViewById(R.id.txtName);
        txtLName = findViewById(R.id.txtLName);
        txtDate = findViewById(R.id.txtDate);
        txtServ = findViewById(R.id.txtServ);
        txtBtests = findViewById(R.id.txtBtests);
        btnS1 = findViewById(R.id.btnS1);
        btnS2 = findViewById(R.id.btnS2);

        String selectDate = getIntent().getStringExtra("datetime");

        FirebaseFirestore.getInstance().collection("bookings")
                .whereEqualTo("datetime", selectDate)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                String patient = document.getString("patient");
                                String location = document.getString("location");
                                String date = document.getString("datetime");

                                // Convert map to string properly
                                Object serviceObj = document.get("services");
                                Object bloodTestsObj = document.get("tests");

                                txtTitle.setText("Results for " + date);
                                txtName.setText(patient);
                                txtLName.setText(location);
                                txtDate.setText(date);
                                txtServ.setText(serviceObj != null ? serviceObj.toString() : "No services");
                                txtBtests.setText(bloodTestsObj != null ? bloodTestsObj.toString() : "No tests");
                            }
                        } else {
                            Toast.makeText(Results.this, "No results found for this date", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Results.this, "Error retrieving results", Toast.LENGTH_SHORT).show();
                    }
                });

        btnS1.setOnClickListener(view -> startActivity(new Intent(Results.this, CalendarPick.class)));

        btnS2.setOnClickListener(view -> startActivity(new Intent(Results.this, MainMenuActivity.class)));
    }
}
