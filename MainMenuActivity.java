//INFO 3245 - Course Project
//Blood Test Booking App with Firebase and Recycler View
//Asmaa Almasri - 100350706
//Howard Chen - 100382934

package com.example.courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMenuActivity extends AppCompatActivity {

    TextView txtRName;
    Button btnbooking, btnresults, btnprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainmenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtRName = findViewById(R.id.txtRName);
        btnbooking = findViewById(R.id.btnbooking);
        btnresults = findViewById(R.id.btnresults);
        btnprofile = findViewById(R.id.btnprofile);

        String patient = getIntent().getStringExtra("name");
        txtRName.setText("Welcome " + patient);

        btnbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, Booking.class);
                startActivity(intent);
            }
        });

        btnresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, CalendarPick.class);
                startActivity(intent);
            }
        });

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, Profile.class);
                startActivity(intent);
            }
        });

    }
}
