package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.myapplication.database.DatabaseHelperProducto

class Eliminar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar)

        val dbHelper = DatabaseHelperProducto(this)
        val cantonNombre = "LORETO"

        // Obtener empresas de Loreto
        val empresas = dbHelper.getAllEmpresasByCantonWithLimit(
            cantonNombre,
            arrayOf(
                DatabaseHelperProducto.COLUMN_EMPRESA_NOMBRE,
                DatabaseHelperProducto.COLUMN_EMPRESA_IMAGEN,
                DatabaseHelperProducto.COLUMN_EMPRESA_SLOGAN
            ),
            10 // Establece el límite de filas que deseas obtener
        )

        // Verificar si hay empresas antes de mostrarlas en el ListView
        if (empresas.isNotEmpty()) {
            // Crear una lista de nombres de empresas para mostrar en el ListView
            val adapter = EmpresaAdapter(this, empresas)
            val listView: ListView = findViewById(R.id.listViewEmpresas)
            listView.adapter = adapter

            // Manejar clics en elementos de la lista
            listView.setOnItemClickListener { _, _, position, _ ->
                // Aquí puedes manejar el clic en un elemento de la lista si es necesario
                // Por ejemplo, puedes abrir otra actividad para mostrar detalles adicionales
                Toast.makeText(this, "Clic en ${empresas[position].nombre}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Manejar el caso en el que no hay empresas en Loreto
            Toast.makeText(this, "No hay empresas en Loreto", Toast.LENGTH_SHORT).show()
        }
    }
}
