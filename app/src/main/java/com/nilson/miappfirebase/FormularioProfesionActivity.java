package com.nilson.miappfirebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormularioProfesionActivity extends AppCompatActivity {

    private EditText etNombreProfesion;
    private MaterialButton btnGuardarProfesion;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_profesion);

        etNombreProfesion = findViewById(R.id.etNombreProfesion);
        btnGuardarProfesion = findViewById(R.id.btnGuardarProfesion);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        btnGuardarProfesion.setOnClickListener(v -> {
            String nombreProfesion = etNombreProfesion.getText().toString().trim();
            String uid = auth.getCurrentUser().getUid();

            if (TextUtils.isEmpty(nombreProfesion)) {
                etNombreProfesion.setError("Nombre requerido");
                return;
            }

            Map<String, Object> profesion = new HashMap<>();
            profesion.put("nombre", nombreProfesion);
            profesion.put("uid", uid); // 🔐 Control por usuario

            db.collection("profesiones")
                    .add(profesion)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Profesión guardada correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
