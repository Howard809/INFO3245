//INFO 3245 - Course Project SignUp.java
//Blood Test Booking App with Firebase and Recycler View
//Asmaa Almasri - 100350706
//Howard Chen - 100382934

package com.example.courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText edtName, edtEmail1, edtPhone, edtDate, edtPassword;
    private Button btnCreate;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        edtName     = findViewById(R.id.edtName);
        edtEmail1   = findViewById(R.id.edtEmail1);
        edtPhone    = findViewById(R.id.edtPhone);
        edtDate     = findViewById(R.id.edtDate);
        edtPassword = findViewById(R.id.edtPassword);
        btnCreate   = findViewById(R.id.btnCeate);
        progressBar = findViewById(R.id.progressBar);

        btnCreate.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String name     = edtName.getText().toString().trim();
            String email    = edtEmail1.getText().toString().trim();
            String phone    = edtPhone.getText().toString().trim();
            String dob      = edtDate.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password are required.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            Map<String, Object> patient = new HashMap<>();
                            patient.put("name", name);
                            patient.put("email", email);
                            patient.put("phone", phone);
                            patient.put("dob", dob);

                            db.collection("patients").document(uid)
                                    .set(patient)
                                    .addOnSuccessListener(aVoid -> {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this,
                                                "Account created & info saved!",
                                                Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignUp.this, SignIn.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this,
                                                "Failed to save patient info: " + e.getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignUp.this,
                                    "Sign up failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
