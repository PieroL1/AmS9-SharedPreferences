package com.example.menuwebview

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCargar: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Referencias a vistas
        etNombre = findViewById(R.id.etNombre)
        etEdad = findViewById(R.id.etEdad)
        etEmail = findViewById(R.id.etEmail)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCargar = findViewById(R.id.btnCargar)

        prefs = getSharedPreferences("mis_preferencias", MODE_PRIVATE)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val edad = etEdad.text.toString()
            val email = etEmail.text.toString()

            prefs.edit()
                .putString("perfil_nombre", nombre)
                .putString("perfil_edad", edad)
                .putString("perfil_email", email)
                .apply()

            Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show()
        }

        btnCargar.setOnClickListener {
            val nombre = prefs.getString("perfil_nombre", "")
            val edad = prefs.getString("perfil_edad", "")
            val email = prefs.getString("perfil_email", "")

            etNombre.setText(nombre)
            etEdad.setText(edad)
            etEmail.setText(email)

            Toast.makeText(this, "Perfil cargado", Toast.LENGTH_SHORT).show()
        }
    }
}
