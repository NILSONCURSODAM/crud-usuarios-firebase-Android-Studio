package com.nilson.miappfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.button.MaterialButton;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etAlias, etPassword;
    private MaterialButton btnRegistrarse;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etAlias = findViewById(R.id.etAlias);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegistrarse.setOnClickListener(v -> {
            String alias = etAlias.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(alias) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Alias y contraseña requeridos", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(alias + "@miapp.com", password)
                    .addOnSuccessListener(authResult -> {
                        String uid = authResult.getUser().getUid();

                        Map<String, Object> datos = new HashMap<>();
                        datos.put("alias", alias);
                        datos.put("uid", uid);

                        db.collection("usuariosAuth")
                                .document(uid)
                                .set(datos)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Registro correcto", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, MainActivity.class));
                                    finish();
                                });
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        });
    }
}
