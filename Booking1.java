//INFO 3245 - Course Project Booking1.java
//Blood Test Booking App with Firebase and Recycler View
//Asmaa Almasri - 100350706
//Howard Chen - 100382934

package com.example.courseproject;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Booking1 extends AppCompatActivity {

    TextView txttitle, txtDateTime, txtservice, txtbloodtests;
    Button btnPickDate, btnPickTime, btnContinue, btnConfirm;
    Calendar selectedDateTime;
    String selectedCity, patientName;
    String bookingId = null;

    LinearLayout linear, bloodtests;
    CheckBox chB, chU, chE, chIn1, chIn2, chIn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        txttitle = findViewById(R.id.txttitle);
        txtDateTime = findViewById(R.id.txtDateTime);
        txtservice = findViewById(R.id.txtservice);
        txtbloodtests = findViewById(R.id.txtbloodtests);

        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);
        btnContinue = findViewById(R.id.btnContinue);
        btnConfirm = findViewById(R.id.btnConfirm);

        linear = findViewById(R.id.linear);
        bloodtests = findViewById(R.id.bloodtests);

        chB = findViewById(R.id.chB);
        chU = findViewById(R.id.chU);
        chE = findViewById(R.id.chE);
        chIn1 = findViewById(R.id.chIn1);
        chIn2 = findViewById(R.id.chIn2);
        chIn3 = findViewById(R.id.chIn3);

        selectedDateTime = Calendar.getInstance();
        selectedCity = getIntent().getStringExtra("location");
        patientName = getIntent().getStringExtra("patientName");

        txtservice.setVisibility(View.INVISIBLE);
        txtbloodtests.setVisibility(View.INVISIBLE);
        linear.setVisibility(View.INVISIBLE);
        bloodtests.setVisibility(View.INVISIBLE);

        btnPickDate.setOnClickListener(view -> {
            int year = selectedDateTime.get(Calendar.YEAR);
            int month = selectedDateTime.get(Calendar.MONTH);
            int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (v, y, m, d) -> {
                selectedDateTime.set(Calendar.YEAR, y);
                selectedDateTime.set(Calendar.MONTH, m);
                selectedDateTime.set(Calendar.DAY_OF_MONTH, d);
                updateLabel();
            }, year, month, day).show();
        });

        btnPickTime.setOnClickListener(view -> {
            int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
            int minute = selectedDateTime.get(Calendar.MINUTE);

            new TimePickerDialog(this, (v, h, m) -> {
                selectedDateTime.set(Calendar.HOUR_OF_DAY, h);
                selectedDateTime.set(Calendar.MINUTE, m);
                updateLabel();
            }, hour, minute, true).show();

        });

        btnContinue.setOnClickListener(view -> {
            txtservice.setVisibility(View.VISIBLE);
            linear.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Please select services", Toast.LENGTH_SHORT).show();
            txtDateTime.setVisibility(View.INVISIBLE);
        });


        chB.setOnClickListener(view -> {
            txtbloodtests.setVisibility(View.VISIBLE);
            bloodtests.setVisibility(View.VISIBLE);
        });

        btnConfirm.setOnClickListener(view -> {
            String dateTime = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", selectedDateTime).toString();

            Map<String, Object> data = new HashMap<>();
            data.put("location", selectedCity);
            data.put("datetime", dateTime);
            data.put("patient", patientName);

            Map<String, Object> services = new HashMap<>();
            if (chB.isChecked()) services.put("Blood Test", true);
            if (chU.isChecked()) services.put("Urine Test", true);
            if (chE.isChecked()) services.put("ECG", true);
            data.put("services", services);

            Map<String, Object> tests = new HashMap<>();
            if (chIn1.isChecked()) tests.put("Vitamin D", true);
            if (chIn2.isChecked()) tests.put("Measles", true);
            if (chIn3.isChecked()) tests.put("Cotinine", true);
            data.put("tests", tests);

            FirebaseFirestore.getInstance().collection("bookings")
                    .add(data)
                    .addOnSuccessListener(doc -> {
                        Intent intent = new Intent(Booking1.this, Confirmation.class);
                        intent.putExtra("name", patientName);
                        intent.putExtra("location", selectedCity);
                        intent.putExtra("datetime", dateTime);
                        intent.putExtra("services", services.toString());
                        intent.putExtra("tests", tests.toString());
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error saving booking", Toast.LENGTH_SHORT).show();
                    });
        });

    }

    private void updateLabel() {
        String dateTime = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", selectedDateTime).toString();
        txtDateTime.setText("Selected: " + dateTime);
    }
}
