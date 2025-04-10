package com.example.courseproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Booking extends AppCompatActivity {

    TextView txttitle, txtrmd, txtdta, txtsur;
    ImageView imgrmd, imgdta, imgsur;
    Button btnS2;
    FirebaseFirestore firestore;

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

        txttitle = findViewById(R.id.txttitle);
        txtrmd = findViewById(R.id.txtrmd);
        txtdta = findViewById(R.id.txtdta);
        txtsur = findViewById(R.id.txtsur);
        imgrmd = findViewById(R.id.imgrmd);
        imgdta = findViewById(R.id.imgdta);
        imgsur = findViewById(R.id.imgsur);
        btnS2 = findViewById(R.id.btnS2);

        imgrmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore = FirebaseFirestore.getInstance();
                Map<String,Object> location = new HashMap<>();
                location.put("location","Richmond");
                firestore.collection("locations")
                        .add(location)
                        .addOnSuccessListener(documentReference -> {
                            Intent intent = new Intent(Booking.this, Booking1.class);
                            intent.putExtra("location","Richmond");
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Booking.this, "Error saving location", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        imgdta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore = FirebaseFirestore.getInstance();
                Map<String,Object> location = new HashMap<>();
                location.put("location","Delta");
                firestore.collection("locations")
                        .add(location)
                        .addOnSuccessListener(documentReference -> {
                            Intent intent = new Intent(Booking.this, Booking1.class);
                            intent.putExtra("location","Delta");
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Booking.this, "Error saving location", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        imgsur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore = FirebaseFirestore.getInstance();
                Map<String,Object> location = new HashMap<>();
                location.put("location","Surrey");
                firestore.collection("locations")
                        .add(location)
                        .addOnSuccessListener(documentReference -> {
                            Intent intent = new Intent(Booking.this, Booking1.class);
                            intent.putExtra("location","Surrey");
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Booking.this, "Error saving location", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        btnS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Booking.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}