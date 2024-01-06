package com.example.myapplication.database

import android.graphics.Bitmap

data class Empresa(
    val id: Long = -1,
    val nombre: String,
    val slogan: String,
    val nombrePropietario: String,
    val facebook: String,
    val instagram: String,
    val whatsapp: String,
    val longitud: Double,
    val latitud: Double,
    val imagen_empresa: ByteArray?,
    val imagen_propietario: ByteArray?,
    val video_empresa: ByteArray?,
    val fkEmpresaCanton: Long
) {
    companion object {
        const val TABLE_NAME = "empresa"
        const val COLUMN_ID = "id_empresa"
        const val COLUMN_NOMBRE = "nombre_empresa"
        const val COLUMN_SLOGAN = "slogan"
        const val COLUMN_NOMBRE_PROPIETARIO = "nombre_propietario"
        const val COLUMN_FACEBOOK = "facebook"
        const val COLUMN_INSTAGRAM = "instagram"
        const val COLUMN_WHATSAPP = "whatsapp"
        const val COLUMN_LONGITUD = "longitud"
        const val COLUMN_LATITUD = "latitud"
        const val COLUMN_IMAGEN_EMPRESA = "imagen_empresa"
        const val COLUMN_IMAGEN_PROPIETARIO = "imagen_propietario"
        const val COLUMN_VIDEO_EMPRESA = "video_empresa"
        const val COLUMN_FK_EMPRESA_CANTON = "fk_empresa_canton"

        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_SLOGAN TEXT, " +
                "$COLUMN_NOMBRE_PROPIETARIO TEXT, " +
                "$COLUMN_FACEBOOK TEXT, " +
                "$COLUMN_INSTAGRAM TEXT, " +
                "$COLUMN_WHATSAPP TEXT, " +
                "$COLUMN_LONGITUD REAL, " +
                "$COLUMN_LATITUD REAL, " +
                "$COLUMN_IMAGEN_EMPRESA BLOB, " +
                "$COLUMN_IMAGEN_PROPIETARIO BLOB, " +
                "$COLUMN_VIDEO_EMPRESA BLOB, " +
                "$COLUMN_FK_EMPRESA_CANTON INTEGER, " +
                "FOREIGN KEY($COLUMN_FK_EMPRESA_CANTON) REFERENCES ${Canton.TABLE_NAME}(${Canton.COLUMN_ID}))"
    }
}
