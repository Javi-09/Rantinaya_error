package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Empresa

class MostrarEmpresaLoretoProducto : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmpresaAdapter
    private lateinit var databaseHelper: DBHelperProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_empresa_loreto_producto)

        recyclerView = findViewById(R.id.recyclerViewEmpresas)
        databaseHelper = DBHelperProducto(this)

        // Obtener y mostrar las empresas de Loreto
        val empresasLoretoP = databaseHelper.getEmpresasByCantonId(3)  // Cambia por el id_canton deseado

        // Configurar el RecyclerView
        adapter = EmpresaAdapter(empresasLoretoP)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
