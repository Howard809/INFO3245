package com.example.courseproject;

import android.os.Bundle;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class Confirmation extends AppCompatActivity {

    TextView txtTitle, txtName, txtLName, txtDate, txtServ, txtBtests;
    Button btnS2;

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
        btnS2 = findViewById(R.id.btnS2);

        String patient = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");
        String date = getIntent().getStringExtra("datetime");
        String service = getIntent().getStringExtra("services");
        String bloodtests = getIntent().getStringExtra("tests");

        txtName.setText("" + patient);
        txtLName.setText("" + location);
        txtDate.setText("" + date);
        txtServ.setText("" + service);
        txtBtests.setText("" + bloodtests);

        btnS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Confirmation.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}