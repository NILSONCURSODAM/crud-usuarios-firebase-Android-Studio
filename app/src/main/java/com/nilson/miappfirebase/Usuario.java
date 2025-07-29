package com.nilson.miappfirebase;

public class Usuario {
    private String id;
    private String nombre;
    private String profesion_id;
    private String uid;
    private String profesion; // ✅ Nombre legible de la profesión (ej: “panadero”)

    public Usuario() {}

    public Usuario(String nombre, String profesion_id, String uid) {
        this.nombre = nombre;
        this.profesion_id = profesion_id;
        this.uid = uid;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getProfesion_id() { return profesion_id; }
    public void setProfesion_id(String profesion_id) { this.profesion_id = profesion_id; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getProfesion() { return profesion; }             // ✅ Nuevo getter
    public void setProfesion(String profesion) { this.profesion = profesion; } // ✅ Nuevo setter
}
