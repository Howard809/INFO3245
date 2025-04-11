package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class Booking extends AppCompatActivity {

    TextView txttitle, txtrmd, txtdta, txtsur;
    ImageView imgrmd, imgdta, imgsur;
    Button btnS2;
    FirebaseFirestore firestore;

    // Patient data from MainMenuActivity
    String patientName, patientEmail, patientPhone, patientDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Receive patient data from Intent
        patientName = getIntent().getStringExtra("name");
        patientEmail = getIntent().getStringExtra("email");
        patientPhone = getIntent().getStringExtra("phone");
        patientDob = getIntent().getStringExtra("dob");

        txttitle = findViewById(R.id.txttitle);
        txtrmd = findViewById(R.id.txtrmd);
        txtdta = findViewById(R.id.txtdta);
        txtsur = findViewById(R.id.txtsur);
        imgrmd = findViewById(R.id.imgrmd);
        imgdta = findViewById(R.id.imgdta);
        imgsur = findViewById(R.id.imgsur);
        btnS2 = findViewById(R.id.btnS2);

        firestore = FirebaseFirestore.getInstance();

        View.OnClickListener locationClickListener = view -> {
            final String[] location = {""}; 

            int viewId = view.getId();
            if (viewId == R.id.imgrmd) location[0] = "Richmond";
            else if (viewId == R.id.imgdta) location[0] = "Delta";
            else if (viewId == R.id.imgsur) location[0] = "Surrey";

            Log.d("Booking", "Selected location: " + location[0] + " (View ID: " + viewId + ")");

            if (location[0].isEmpty()) {
                Toast.makeText(Booking.this, "Please select a valid location.", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> locationMap = new HashMap<>();
            locationMap.put("location", location[0]);

            firestore.collection("locations")
                    .add(locationMap)
                    .addOnSuccessListener(documentReference -> {
                        Intent intent = new Intent(Booking.this, Booking1.class);
                        intent.putExtra("location", location[0]);
                        intent.putExtra("name", patientName);
                        intent.putExtra("email", patientEmail);
                        intent.putExtra("phone", patientPhone);
                        intent.putExtra("dob", patientDob);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(Booking.this, "Error saving location", Toast.LENGTH_SHORT).show());
        };

        imgrmd.setOnClickListener(locationClickListener);
        imgdta.setOnClickListener(locationClickListener);
        imgsur.setOnClickListener(locationClickListener);

        btnS2.setOnClickListener(view -> {
            Intent intent = new Intent(Booking.this, MainMenuActivity.class);
            intent.putExtra("name", patientName);
            intent.putExtra("email", patientEmail);
            intent.putExtra("phone", patientPhone);
            intent.putExtra("dob", patientDob);
            startActivity(intent);
            finish();
        });
    }
}
