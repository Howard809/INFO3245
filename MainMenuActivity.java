package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMenuActivity extends AppCompatActivity {

    TextView txtRName;
    Button btnbooking, btnresults, btnprofile, btnLogout;

    String patientName, patientEmail, patientPhone, patientDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtRName = findViewById(R.id.txtRName);
        btnbooking = findViewById(R.id.btnbooking);
        btnresults = findViewById(R.id.btnresults);
        btnprofile = findViewById(R.id.btnprofile);
        btnLogout = findViewById(R.id.btnLogout);

        // Safely get patient data from Intent
        patientName = getIntent().getStringExtra("name");
        patientEmail = getIntent().getStringExtra("email");
        patientPhone = getIntent().getStringExtra("phone");
        patientDob = getIntent().getStringExtra("dob");

        if (patientName != null && !patientName.isEmpty()) {
            txtRName.setText("Welcome " + patientName);
        } else {
            txtRName.setText("Welcome!");
        }

        btnbooking.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, Booking.class);
            intent.putExtra("name", patientName);
            intent.putExtra("email", patientEmail);
            intent.putExtra("phone", patientPhone);
            intent.putExtra("dob", patientDob);
            startActivity(intent);
        });

        btnresults.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, CalendarPick.class);
            intent.putExtra("name", patientName);
            intent.putExtra("email", patientEmail);
            intent.putExtra("phone", patientPhone);
            intent.putExtra("dob", patientDob);
            startActivity(intent);
        });

        btnprofile.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, Profile.class);
            intent.putExtra("name", patientName);
            intent.putExtra("email", patientEmail);
            intent.putExtra("phone", patientPhone);
            intent.putExtra("dob", patientDob);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
