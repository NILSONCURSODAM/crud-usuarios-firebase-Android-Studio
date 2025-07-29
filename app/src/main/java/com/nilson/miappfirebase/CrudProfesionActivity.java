package com.nilson.miappfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;

public class CrudProfesionActivity extends AppCompatActivity {

    private RecyclerView recyclerProfesiones;
    private MaterialButton btnAgregarProfesion;
    private ProfesionAdapter adapter;
    private ArrayList<Profesion> listaProfesiones;
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_profesion);

        recyclerProfesiones = findViewById(R.id.recyclerProfesiones);
        btnAgregarProfesion = findViewById(R.id.btnAgregarProfesion);
        recyclerProfesiones.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        listaProfesiones = new ArrayList<>();
        adapter = new ProfesionAdapter(listaProfesiones);
        recyclerProfesiones.setAdapter(adapter);

        btnAgregarProfesion.setOnClickListener(v -> {
            startActivity(new Intent(this, FormularioProfesionActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProfesiones();
    }

    private void cargarProfesiones() {
        db.collection("profesiones")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    listaProfesiones.clear();
                    for (DocumentSnapshot doc : query) {
                        Profesion profesion = doc.toObject(Profesion.class);
                        profesion.setId(doc.getId());
                        listaProfesiones.add(profesion);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar profesiones: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
