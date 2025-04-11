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

public class Confirmation extends AppCompatActivity {

    TextView txtTitle, txtName, txtLName, txtDate, txtServ, txtBtests;
    Button btnMainMenu, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation);

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
        btnMainMenu = findViewById(R.id.btnMainMenu);
        btnLogout = findViewById(R.id.btnLogout);

        String patient = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");
        String date = getIntent().getStringExtra("datetime");
        String service = getIntent().getStringExtra("services");
        String bloodtests = getIntent().getStringExtra("tests");

        txtTitle.setText("Booking Confirmation");
        txtName.setText("Patient Name: " + (patient != null ? patient : "N/A"));
        txtLName.setText("Location: " + (location != null ? location : "N/A"));
        txtDate.setText("Date & Time: " + (date != null ? date : "N/A"));
        txtServ.setText(service != null ? service : "No services selected");
        txtBtests.setText(bloodtests != null ? bloodtests : "No tests selected");

        // Return to Main Menu
        btnMainMenu.setOnClickListener(view -> {
            Intent intent = new Intent(Confirmation.this, MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        // Logout → Go to MainActivity (Sign In / Sign Up screen)
        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(Confirmation.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
