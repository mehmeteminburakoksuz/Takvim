package com.example.takvimnothaziran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText tcNoEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private Button logOnButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        tcNoEditText = findViewById(R.id.tcNo);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        addressEditText = findViewById(R.id.address);
        logOnButton = findViewById(R.id.LogOnButton);

        logOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String tcNo = tcNoEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                if (validateInput(firstName, lastName, username, password, tcNo, phone, email, address)) {
                    createUser(firstName, lastName, username, password, tcNo, phone, email, address);
                }
            }
        });
    }

    private boolean validateInput(String firstName, String lastName, String username, String password, String tcNo, String phone, String email, String address) {
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || tcNo.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createUser(String firstName, String lastName, String username, String password, String tcNo, String phone, String email, String address) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Kullanıcı oluşturulduysa Firestore'a kaydet
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            User user = new User(firstName, lastName, username, password, tcNo, phone, email, address);
                            DocumentReference userRef = db.collection("users").document(userId);

                            userRef.set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Kullanıcı oluşturma başarısız olduysa hata mesajını göster
                            Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
