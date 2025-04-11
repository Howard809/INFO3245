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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    TextView txtTitle, txtName, txtLName, txtDate, txtServ, txtBtests;
    Button btnS1, btnS2;
    RecyclerView rvr;
    ResultsAdapter rA;
    List<BookingR> rL = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        rvr = findViewById(R.id.rvr);
        rvr.setLayoutManager(new LinearLayoutManager(this));
        rA = new ResultsAdapter(rL);
        rvr.setAdapter(rA);

        txtTitle = findViewById(R.id.txtTitle);
        //txtName = findViewById(R.id.txtName);
        //txtLName = findViewById(R.id.txtLName);
        //txtDate = findViewById(R.id.txtDate);
        //txtServ = findViewById(R.id.txtServ);
        //txtBtests = findViewById(R.id.txtBtests);
        btnS1 = findViewById(R.id.btnS1);
        btnS2 = findViewById(R.id.btnS2);

        String selectDate = getIntent().getStringExtra("datetime");

        db.collection("bookings")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String datetime = doc.getString("datetime");
                        if (datetime != null && datetime.startsWith(selectDate)) {
                            String name = doc.getString("name");
                            String service = doc.getString("service");

                            BookingR result = new BookingR(name, service, datetime);
                            rL.add(result);
                        }
                    }
                    rA.notifyDataSetChanged();

                    if (rL.isEmpty()) {
                        Toast.makeText(Results.this, "No results found for " + selectDate, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Results.this, "Failed to fetch results", Toast.LENGTH_SHORT).show();
                });

        btnS1.setOnClickListener(view -> startActivity(new Intent(Results.this, CalendarPick.class)));

        btnS2.setOnClickListener(view -> startActivity(new Intent(Results.this, MainMenuActivity.class)));
    }
}
