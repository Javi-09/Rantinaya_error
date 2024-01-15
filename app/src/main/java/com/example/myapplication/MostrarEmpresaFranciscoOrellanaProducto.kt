package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Empresa

class MostrarEmpresaFranciscoOrellanaProducto : AppCompatActivity(), EmpresaAdapter.ClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmpresaAdapter
    private lateinit var databaseHelper: DBHelperProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_empresa_francisco_orellana_producto)

        recyclerView = findViewById(R.id.recyclerViewEmpresas)
        databaseHelper = DBHelperProducto(this)

        // Obtener y mostrar las empresas de Loreto
        val empresasFOP = databaseHelper.getEmpresasByCantonId(1)  // Cambia por el id_canton deseado

        // Configurar el RecyclerView
        adapter = EmpresaAdapter(empresasFOP, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onVerEmpresaClick(position: Int) {
        val empresaSeleccionada = adapter.empresas[position]

        // Crear un Intent para la nueva actividad
        val intent = Intent(this, DetalleEmpresaProducto::class.java)

        // Puedes pasar el ID de la empresa a la nueva actividad
        intent.putExtra("empresa_id", empresaSeleccionada.id)

        // Iniciar la nueva actividad
        startActivity(intent)
    }
}
