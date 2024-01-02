package com.example.myapplication.database


// Clase para representar la entidad Producto
data class Producto(
    val id: Long = -1,
    val nombre: String,
    val imagenProducto: ByteArray,
    val precio: Double,
    val descripcion: String,
    val idEmpresa: Long // Clave foránea que referencia a la Empresa
)