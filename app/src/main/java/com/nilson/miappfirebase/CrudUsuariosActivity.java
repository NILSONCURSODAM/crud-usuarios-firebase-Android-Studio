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
import java.util.HashMap;
import java.util.Map;

public class CrudUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerUsuarios;
    private MaterialButton btnAgregarUsuario;
    private UsuarioAdapter adapter;
    private ArrayList<Usuario> listaUsuarios;
    private FirebaseFirestore db;
    private String uid;

    // ⚡ Mapa de profesiones (id -> nombre)
    private Map<String, String> mapaProfesiones = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuarios);

        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        listaUsuarios = new ArrayList<>();
        adapter = new UsuarioAdapter(this, listaUsuarios);
        recyclerUsuarios.setAdapter(adapter);

        btnAgregarUsuario.setOnClickListener(v -> {
            startActivity(new Intent(this, FormularioUsuarioActivity.class));
        });

        cargarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    // ✅ Método principal que carga profesiones y luego usuarios
    private void cargarDatos() {
        db.collection("profesiones")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    mapaProfesiones.clear();
                    for (DocumentSnapshot doc : query) {
                        mapaProfesiones.put(doc.getId(), doc.getString("nombre"));
                    }
                    cargarUsuarios();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar profesiones: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void cargarUsuarios() {
        db.collection("usuarios")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    listaUsuarios.clear();
                    for (DocumentSnapshot doc : query) {
                        Usuario usuario = doc.toObject(Usuario.class);
                        usuario.setId(doc.getId());

                        // 🔁 Convertimos profesion_id en nombre
                        String profesionId = usuario.getProfesion_id();
                        String nombreProfesion = mapaProfesiones.getOrDefault(profesionId, "Desconocida");
                        usuario.setProfesion(nombreProfesion);

                        listaUsuarios.add(usuario);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar usuarios: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    public void eliminarUsuario(String usuarioId) {
        db.collection("usuarios")
                .document(usuarioId)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    cargarDatos(); // ✅ Actualizar lista completa
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error eliminando: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
