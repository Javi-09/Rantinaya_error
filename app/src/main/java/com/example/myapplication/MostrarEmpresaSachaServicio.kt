package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperServicio
import com.example.myapplication.database.Empresa

class MostrarEmpresaSachaServicio : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmpresaServicioAdapter
    private lateinit var databaseHelper: DBHelperServicio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_empresa_sacha_servicio)

        recyclerView = findViewById(R.id.recyclerViewEmpresas)
        databaseHelper = DBHelperServicio(this)

        // Obtener y mostrar las empresas de Loreto
        val empresasSachaS = databaseHelper.getEmpresasByCantonId(2)  // Cambia por el id_canton deseado

        // Configurar el RecyclerView
        adapter = EmpresaServicioAdapter(empresasSachaS)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}