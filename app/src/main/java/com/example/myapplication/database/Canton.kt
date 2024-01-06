package com.example.myapplication.database

data class Canton(
    val id: Long = -1,
    val nombreCanton: String
) {
    companion object {
        const val TABLE_NAME = "canton"
        const val COLUMN_ID = "id_canton"
        const val COLUMN_NOMBRE_CANTON = "nombre_canton"
        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NOMBRE_CANTON TEXT)"
    }
}
