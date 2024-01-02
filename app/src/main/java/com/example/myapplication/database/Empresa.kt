package com.example.myapplication.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
// Clase para representar la entidad Empresa
data class Empresa(
    val id: Long = -1,
    val nombre: String,
    val imagenEmpresa: ByteArray,
    val slogan: String,
    val video: ByteArray,
    val nombrePropietario: String,
    val imagenPropietario: ByteArray,
    val facebook: String,
    val instagram: String,
    val whatsapp: String,
    val idCanton: Long
) {
    companion object {
        // Función para convertir ByteArray a Bitmap
        fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        // Función para convertir Bitmap a ByteArray
        fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }
    }
}
