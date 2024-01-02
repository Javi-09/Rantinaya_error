package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.DatabaseHelperProducto

class EmpresaLoretoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmpresaAdapter
    private lateinit var databaseHelper: DatabaseHelperProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_loreto)

        recyclerView = findViewById(R.id.recyclerViewEmpresas)
        databaseHelper = DatabaseHelperProducto(this)

        try {
            // Define las columnas que necesitas recuperar
            val columnas = arrayOf(
                DatabaseHelperProducto.COLUMN_EMPRESA_NOMBRE,
                DatabaseHelperProducto.COLUMN_EMPRESA_IMAGEN,
                DatabaseHelperProducto.COLUMN_EMPRESA_SLOGAN,
                // Agrega aquí las demás columnas que necesitas
            )

            // Número máximo de filas que deseas recuperar
            val limiteFilas = 10

            // Realiza la consulta con SELECT específico y LIMIT
            val empresasLoreto = databaseHelper.getAllEmpresasByCantonWithLimit("LORETO", columnas, limiteFilas)

            // Configurar el RecyclerView con la lista de empresas
            configurarRecyclerView(empresasLoreto)

        } catch (e: Exception) {
            // Manejar excepciones relacionadas con la obtención de empresas de LORETO
            e.printStackTrace()
            Log.e("EmpresaLoretoActivity", "Error al obtener empresas de LORETO: ${e.message}")
            Toast.makeText(this, "Error al obtener empresas de LORETO", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para configurar el RecyclerView con la lista de empresas
    private fun configurarRecyclerView(empresas: List<com.example.myapplication.database.Empresa>) {
        // Crear el adaptador con la lista de empresas
        adapter = EmpresaAdapter(empresas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
