package com.example.myapplication.database

import android.graphics.Bitmap


data class Producto(
    val id: Long = -1,
    val nombre: String,
    val imagen: Bitmap?,  // Imagen como Bitmap
    val precio: Double,
    val descripcion: String,
    val fkProductoEmpresa: Long
) {
    companion object {
        const val TABLE_NAME = "producto"
        const val COLUMN_ID = "id_producto"
        const val COLUMN_NOMBRE = "nombre_producto"
        const val COLUMN_IMAGEN = "imagen_producto"
        const val COLUMN_PRECIO = "precio_producto"
        const val COLUMN_DESCRIPCION = "descripcion_producto"
        const val COLUMN_FK_PRODUCTO_EMPRESA = "fk_producto_empresa"
        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_IMAGEN BLOB, " +  // Tipo BLOB para imágenes
                "$COLUMN_PRECIO REAL, " +
                "$COLUMN_DESCRIPCION TEXT, " +
                "$COLUMN_FK_PRODUCTO_EMPRESA INTEGER, " +
                "FOREIGN KEY($COLUMN_FK_PRODUCTO_EMPRESA) REFERENCES ${Empresa.TABLE_NAME}(${Empresa.COLUMN_ID}))"
    }
}
