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

public class Profile extends AppCompatActivity {

    TextView txtTitle, txtName, txtLName, txtDate, txtServ;
    Button btnS2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

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
        btnS2 = findViewById(R.id.btnS2);

        // Get patient data from Intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String dob = getIntent().getStringExtra("dob");
        String phone = getIntent().getStringExtra("phone");

        txtName.setText("Name: " + name);
        txtLName.setText("Email: " + email);
        txtDate.setText("Date of Birth: " + dob);
        txtServ.setText("Phone: " + phone);

        btnS2.setOnClickListener(view -> startActivity(new Intent(Profile.this, MainMenuActivity.class)));
    }
}
