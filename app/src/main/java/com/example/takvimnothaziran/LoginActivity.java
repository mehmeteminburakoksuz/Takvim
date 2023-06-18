package com.example.takvimnothaziran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerSButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase kimlik doğrulama referansını al
        mAuth = FirebaseAuth.getInstance();

        // Gerekli bileşenleri arayüzden al
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerSButton = findViewById(R.id.registerSButton);

        // Giriş düğmesine tıklandığında işlemi başlat
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Kullanıcıyı giriş yapmaya çalış
                signInUser(username, password);
            }
        });

        // Kayıt düğmesine tıklandığında kayıt aktivitesini başlat
        registerSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    // Kullanıcı girişini sağla
    private void signInUser(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Giriş başarılıysa ana aktiviteye yönlendir
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Giriş Başarılı!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // Giriş başarısızsa hata mesajını göster
                        Toast.makeText(LoginActivity.this, "Giriş Hatası: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
