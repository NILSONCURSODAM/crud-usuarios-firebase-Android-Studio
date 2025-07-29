package com.nilson.miappfirebase;

public class Profesion {
    private String id;
    private String nombre;
    private String uid;

    public Profesion() {} // 🔄 Necesario para Firestore

    public Profesion(String nombre, String uid) {
        this.nombre = nombre;
        this.uid = uid;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
}
