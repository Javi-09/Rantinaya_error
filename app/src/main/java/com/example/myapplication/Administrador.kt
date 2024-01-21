package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R

class Administrador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador)


        val btnServicio: Button = findViewById(R.id.btnServicio)
        val btnProducto: Button = findViewById(R.id.btnProducto)

        btnServicio.setOnClickListener {
            // Acción al presionar el botón de Servicio
            val intent = Intent(this, IngresarEmpresaServicio::class.java)
            startActivity(intent)
        }

        btnProducto.setOnClickListener {
            // Acción al presionar el botón de Producto
            val intent = Intent(this, IngresarEmpresaProducto::class.java)
            startActivity(intent)
        }
    }
}
