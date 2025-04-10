//INFO 3245 - Course Project CalendarPick.java
//Blood Test Booking App with Firebase and Recycler View
//Asmaa Almasri - 100350706
//Howard Chen - 100382934

package com.example.courseproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CalendarPick extends AppCompatActivity {

    TextView txtDateTime;
    Button btnPD, btnPT, btnS1, btnS2;
    Calendar selectDate;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    DateAdapter dateAdapter;
    List<String> availableDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendarpick);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPD = findViewById(R.id.btnPD);
        btnPT = findViewById(R.id.btnPT);
        btnS1 = findViewById(R.id.btnS1);
        btnS2 = findViewById(R.id.btnS2);
        txtDateTime = findViewById(R.id.txtDateTime);
        selectDate = Calendar.getInstance();
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dateAdapter = new DateAdapter(availableDates, new DateAdapter.OnDateClickListener() {
            @Override
            public void onDateClick(String date) {
                // Handle date click event
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    java.util.Date parsedDate = sdf.parse(date);
                    if (parsedDate != null) {
                        selectDate.setTime(parsedDate);
                        updateLabel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(dateAdapter);

        fetchBookingDates();

        btnPD.setOnClickListener(view -> {

            int year = selectDate.get(java.util.Calendar.YEAR);
            int month = selectDate.get(java.util.Calendar.MONTH);
            int day = selectDate.get(java.util.Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (v, y, m, d) -> {
                selectDate.set(java.util.Calendar.YEAR, y);
                selectDate.set(java.util.Calendar.MONTH, m);
                selectDate.set(java.util.Calendar.DAY_OF_MONTH, d);
                updateLabel();
            }, year, month, day).show();
        });

        btnPT.setOnClickListener(view -> {
            int hour = selectDate.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = selectDate.get(java.util.Calendar.MINUTE);

            new TimePickerDialog(this, (v, h, m) -> {
                selectDate.set(java.util.Calendar.HOUR_OF_DAY, h);
                selectDate.set(java.util.Calendar.MINUTE, m);
                updateLabel();
            }, hour, minute, true).show();
        });

        btnS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String formattedDate = android.text.format.DateFormat.format("yyyy-MM-dd", selectDate).toString();
                Intent intent = new Intent(CalendarPick.this, Results.class);
                intent.putExtra("datetime", formattedDate);
                startActivity(intent);
            }
        });

        btnS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarPick.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void fetchBookingDates() {
        db.collection("bookings")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                            for (DocumentSnapshot document : documents) {
                                String datetime = document.getString("datetime");
                                availableDates.add(datetime);
                            }
                            dateAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(CalendarPick.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateLabel() {
        String dateTime = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", selectDate).toString();
        txtDateTime.setText("Selected: " + dateTime);
    }
}
