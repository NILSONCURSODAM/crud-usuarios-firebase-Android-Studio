# 📱 MiAppCrudFirebase

Aplicación Android de gestión de usuarios y profesiones con autenticación integrada mediante Firebase. Proyecto CRUD modular y funcional, desarrollado en Java con Android Studio, Firebase Authentication y Firestore.

---

## 🚀 Descripción del Proyecto

MiAppCrudFirebase es una app Android nativa diseñada para registrar, autenticar y gestionar usuarios asociados a profesiones. Permite a los usuarios autenticarse con alias y contraseña, y realizar operaciones CRUD sobre las entidades disponibles, todo de forma segura y privada mediante UID.

La app es totalmente funcional, modular y escalable, cumpliendo buenas prácticas de arquitectura y con una interfaz moderna basada en Material Design 3.

---

## 🛠️ Tecnologías utilizadas

- **Android Studio (Java)**
- **Firebase Authentication**
- **Cloud Firestore**
- **Material Components for Android**
- **ConstraintLayout**
- **RecyclerView**
- **Módulo login/register + CRUD completo**

---

## 🔐 Funcionalidades principales

- Registro de usuarios con alias, nombre y contraseña
- Inicio de sesión con validación y persistencia por UID
- CRUD de profesiones:
    - Crear, ver, editar y eliminar profesiones
- CRUD de usuarios:
    - Crear, ver, editar y eliminar usuarios
    - Asignación de profesiones mediante Spinner
    - Creación de nueva profesión directamente desde el Spinner
- Carga dinámica de profesiones guardadas en Firestore
- Validaciones visuales con `TextInputLayout.setError()`
- Arquitectura modular y organizada: `activities`, `adapters`, `models`, `utils`, `firebase`
- App protegida por UID: cada usuario solo ve sus propios datos

---

## 📂 Estructura de Carpetas

```
MiAppCrudFirebase/
├── app/src/main/java/com/nilson/miappfirebase/
│   ├── activities (Splash, Login, Register, CRUDs...)
│   ├── adapters (UsuarioAdapter, ProfesionAdapter...)
│   ├── models (Usuario.java, Profesion.java...)
│   ├── firebase (FirebaseAuthManager, FirebaseRefs...)
│   ├── utils (Validaciones, constantes...)
├── res/layout (todos los XML)
├── .gitignore
├── README.md
```

---

## 📷 Capturas de Pantalla

| CRUD Usuarios                | CRUD Profesiones            |
|------------------------------|-----------------------------|
| ![](./screenshots/Registro.png) | ![](./screenshots/Crud.png) |
|                              |                             |   
| LoginActivity                       |                             |
| ![](./screenshots/login.png) |                             |

>

---

## ✅ Cómo usar este proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/NILSONCURSODAM/crud-usuarios-firebase-Android-Studio.git
   ```

2. Abre el proyecto en Android Studio.

3. Crea un proyecto en Firebase y descarga el archivo `google-services.json`:
    - Añádelo a `app/`.

4. Sincroniza el proyecto con Gradle.

5. Ejecuta en un emulador o dispositivo físico.

---

## 📌 Requisitos previos

- Android Studio actualizado
- Firebase configurado con Authentication y Firestore
- Google Play Services y conexión a internet

---

## 📚 Aprendizajes clave

- Integración completa de Firebase Authentication y Firestore
- Gestión de datos con UID personalizado
- Arquitectura escalable y modular en Android Studio
- Validaciones profesionales y buen manejo del estado
- Uso avanzado de `Spinner`, `RecyclerView`, `MaterialButton` y `TextInputLayout`

---

## 🤝 Autor

**Nilson Ochoa Martínez**  
Desarrollador Web & Móvil Junior  
📍 Alicante, España  
💼 [LinkedIn](https://www.linkedin.com/in/nilsonochoa-dev/)  
📧 nilson-ochoamartinez@hotmail.com  
📱 +34 618 766 660

---

## ⭐ Licencia

Este proyecto es de uso libre para fines formativos.  
Si lo usas como base, se agradece la mención al autor.
