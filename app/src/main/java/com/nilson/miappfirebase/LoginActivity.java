package com.nilson.miappfirebase;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🧩 Referencias a los elementos del layout
        TextInputEditText etAlias = findViewById(R.id.etAlias);
        TextInputEditText etPassword = findViewById(R.id.etPassword);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        TextView tvIrRegistro = findViewById(R.id.tvIrRegistro);

        // 🔥 FirebaseAuth
        FirebaseAuth auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            String alias = etAlias.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // ✅ Validación simple
            if (alias.isEmpty()) {
                etAlias.setError("Alias obligatorio");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Contraseña obligatoria");
                return;
            }

            // 🎯 Usamos alias como si fuera email: alias@miapp.com
            String emailFake = alias + "@miapp.com";

            // 🔐 Iniciar sesión con Firebase
            auth.signInWithEmailAndPassword(emailFake, password)
                    .addOnSuccessListener(authResult -> {
                        Toast.makeText(this, "¡Bienvenido " + alias + "!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, CrudUsuariosActivity.class)); // ✅ Ya está en el mismo package
                        finish(); // Cerrar LoginActivity
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

        // 🔁 Ir a RegisterActivity si no tiene cuenta
        tvIrRegistro.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
