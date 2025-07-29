package com.nilson.miappfirebase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import java.util.*;

public class FormularioUsuarioActivity extends AppCompatActivity {

    private EditText etNombre;
    private Spinner spinnerProfesiones;
    private MaterialButton btnGuardar;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private ArrayList<String> nombresProfesiones = new ArrayList<>();
    private Map<String, String> mapaNombreToId = new HashMap<>();
    private ArrayAdapter<String> adaptadorSpinner;

    private static final String OPCION_CREAR_NUEVA = "Crear nueva profesión...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuario);

        etNombre = findViewById(R.id.etNombre);
        spinnerProfesiones = findViewById(R.id.spinnerProfesiones);
        btnGuardar = findViewById(R.id.btnGuardarUsuario);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        cargarProfesiones();

        spinnerProfesiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int position, long id) {
                if (nombresProfesiones.get(position).equals(OPCION_CREAR_NUEVA)) {
                    mostrarDialogoCrearProfesion();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnGuardar.setOnClickListener(v -> guardarUsuario());
    }

    private void cargarProfesiones() {
        String uid = auth.getCurrentUser().getUid();

        db.collection("profesiones")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    nombresProfesiones.clear();
                    mapaNombreToId.clear();

                    for (DocumentSnapshot doc : query) {
                        String id = doc.getId();
                        String nombre = doc.getString("nombre");
                        if (nombre != null) {
                            nombresProfesiones.add(nombre);
                            mapaNombreToId.put(nombre, id);
                        }
                    }

                    nombresProfesiones.add(OPCION_CREAR_NUEVA);

                    adaptadorSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresProfesiones);
                    adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProfesiones.setAdapter(adaptadorSpinner);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar profesiones: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void mostrarDialogoCrearProfesion() {
        EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Nueva profesión")
                .setMessage("Introduce el nombre de la nueva profesión")
                .setView(input)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nuevaProfesion = input.getText().toString().trim();
                    if (!TextUtils.isEmpty(nuevaProfesion)) {
                        guardarNuevaProfesion(nuevaProfesion);
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void guardarNuevaProfesion(String nombreProfesion) {
        String uid = auth.getCurrentUser().getUid();
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombreProfesion);
        data.put("uid", uid);

        db.collection("profesiones")
                .add(data)
                .addOnSuccessListener(docRef -> {
                    Toast.makeText(this, "Profesión creada", Toast.LENGTH_SHORT).show();
                    cargarProfesionesDespuesDeAgregar(nombreProfesion, docRef.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al crear profesión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void cargarProfesionesDespuesDeAgregar(String nombreSeleccionado, String nuevaId) {
        String uid = auth.getCurrentUser().getUid();

        db.collection("profesiones")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    nombresProfesiones.clear();
                    mapaNombreToId.clear();

                    for (DocumentSnapshot doc : query) {
                        String id = doc.getId();
                        String nombre = doc.getString("nombre");
                        if (nombre != null) {
                            nombresProfesiones.add(nombre);
                            mapaNombreToId.put(nombre, id);
                        }
                    }

                    nombresProfesiones.add(OPCION_CREAR_NUEVA);
                    adaptadorSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresProfesiones);
                    adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProfesiones.setAdapter(adaptadorSpinner);

                    int index = nombresProfesiones.indexOf(nombreSeleccionado);
                    if (index >= 0) {
                        spinnerProfesiones.setSelection(index);
                    }
                });
    }

    private void guardarUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String nombreProfesion = (String) spinnerProfesiones.getSelectedItem();
        String profesion_id = mapaNombreToId.get(nombreProfesion);
        String uid = auth.getCurrentUser().getUid();

        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Campo requerido");
            return;
        }

        if (TextUtils.isEmpty(profesion_id)) {
            Toast.makeText(this, "Seleccione una profesión válida", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("profesion_id", profesion_id);
        usuario.put("uid", uid);

        db.collection("usuarios")
                .add(usuario)
                .addOnSuccessListener(docRef -> {
                    Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al guardar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
