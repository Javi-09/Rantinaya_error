package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class DatabaseHelperProducto(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "productos.db"
        const val DATABASE_VERSION = 2

        // Definición de tablas y columnas
        const val TABLE_CANTON = "canton"
        const val COLUMN_CANTON_ID = "_id"
        const val COLUMN_CANTON_NOMBRE = "nombre"

        const val TABLE_EMPRESA = "empresa"
        const val COLUMN_EMPRESA_ID = "_id"
        const val COLUMN_EMPRESA_NOMBRE = "nombre_empresa"
        const val COLUMN_EMPRESA_IMAGEN = "imagen_empresa"
        const val COLUMN_EMPRESA_SLOGAN = "slogan"
        const val COLUMN_EMPRESA_VIDEO = "video"
        const val COLUMN_EMPRESA_NOMBRE_PROPIETARIO = "nombre_propietario"
        const val COLUMN_EMPRESA_IMAGEN_PROPIETARIO = "imagen_propietario"
        const val COLUMN_EMPRESA_FACEBOOK = "facebook"
        const val COLUMN_EMPRESA_INSTAGRAM = "instagram"
        const val COLUMN_EMPRESA_WHATSAPP = "whatsapp"
        const val COLUMN_EMPRESA_ID_CANTON = "id_canton"

        const val TABLE_PRODUCTO = "producto"
        const val COLUMN_PRODUCTO_ID = "_id"
        const val COLUMN_PRODUCTO_NOMBRE = "nombre_producto"
        const val COLUMN_PRODUCTO_IMAGEN = "imagen_producto"
        const val COLUMN_PRODUCTO_PRECIO = "precio"
        const val COLUMN_PRODUCTO_DESCRIPCION = "descripcion"
        const val COLUMN_PRODUCTO_ID_EMPRESA = "id_empresa"

        const val CREATE_TABLE_CANTON = """
            CREATE TABLE $TABLE_CANTON (
                $COLUMN_CANTON_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CANTON_NOMBRE TEXT NOT NULL
            );
        """

        const val CREATE_TABLE_EMPRESA = """
            CREATE TABLE $TABLE_EMPRESA (
                $COLUMN_EMPRESA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EMPRESA_NOMBRE TEXT NOT NULL,
                $COLUMN_EMPRESA_IMAGEN BLOB NOT NULL,
                $COLUMN_EMPRESA_SLOGAN TEXT NOT NULL,
                $COLUMN_EMPRESA_VIDEO BLOB NOT NULL,
                $COLUMN_EMPRESA_NOMBRE_PROPIETARIO TEXT NOT NULL,
                $COLUMN_EMPRESA_IMAGEN_PROPIETARIO BLOB NOT NULL,
                $COLUMN_EMPRESA_FACEBOOK TEXT,
                $COLUMN_EMPRESA_INSTAGRAM TEXT,
                $COLUMN_EMPRESA_WHATSAPP TEXT,
                $COLUMN_EMPRESA_ID_CANTON INTEGER,
                FOREIGN KEY ($COLUMN_EMPRESA_ID_CANTON) REFERENCES $TABLE_CANTON($COLUMN_CANTON_ID)
            );
        """

        const val CREATE_TABLE_PRODUCTO = """
            CREATE TABLE $TABLE_PRODUCTO (
                $COLUMN_PRODUCTO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PRODUCTO_NOMBRE TEXT NOT NULL,
                $COLUMN_PRODUCTO_IMAGEN BLOB NOT NULL,
                $COLUMN_PRODUCTO_PRECIO REAL NOT NULL,
                $COLUMN_PRODUCTO_DESCRIPCION TEXT NOT NULL,
                $COLUMN_PRODUCTO_ID_EMPRESA INTEGER,
                FOREIGN KEY ($COLUMN_PRODUCTO_ID_EMPRESA) REFERENCES $TABLE_EMPRESA($COLUMN_EMPRESA_ID)
            );
        """

        const val DROP_TABLE_CANTON = "DROP TABLE IF EXISTS $TABLE_CANTON;"
        const val DROP_TABLE_EMPRESA = "DROP TABLE IF EXISTS $TABLE_EMPRESA;"
        const val DROP_TABLE_PRODUCTO = "DROP TABLE IF EXISTS $TABLE_PRODUCTO;"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CANTON)

        // Insertar cantones manualmente
        insertCanton("FRANCISCO DE ORELLANA", db)
        insertCanton("LORETO", db)
        insertCanton("SACHA", db)
        insertCanton("AGUARICO", db)

        db.execSQL(CREATE_TABLE_EMPRESA)
        db.execSQL(CREATE_TABLE_PRODUCTO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_CANTON)
        db.execSQL(DROP_TABLE_EMPRESA)
        db.execSQL(DROP_TABLE_PRODUCTO)
        onCreate(db)
    }

    private fun insertCanton(nombre: String, db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put(COLUMN_CANTON_NOMBRE, nombre)
        }
        db.insert(TABLE_CANTON, null, values)
    }

    // Métodos adicionales

    fun getAllCantones(): List<Canton> {
        val cantones = mutableListOf<Canton>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_CANTON,
            arrayOf(COLUMN_CANTON_ID, COLUMN_CANTON_NOMBRE),
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_CANTON_ID))
                val nombre = getString(getColumnIndexOrThrow(COLUMN_CANTON_NOMBRE))
                cantones.add(Canton(id, nombre))
            }
        }
        cursor.close()
        return cantones
    }

    fun getAllEmpresasByCantonWithLimit(
        nombreCanton: String,
        columnas: Array<String>,
        limiteFilas: Int
    ): List<Empresa> {
        val empresas = mutableListOf<Empresa>()
        val db = readableDatabase
        val columnsString = columnas.joinToString(", ")

        val query = """
            SELECT $columnsString FROM $TABLE_EMPRESA
            INNER JOIN $TABLE_CANTON ON $TABLE_EMPRESA.$COLUMN_EMPRESA_ID_CANTON = $TABLE_CANTON.$COLUMN_CANTON_ID
            WHERE $TABLE_CANTON.$COLUMN_CANTON_NOMBRE = ?
            LIMIT $limiteFilas
        """.trimIndent()

        val cursor: Cursor = db.rawQuery(query, arrayOf(nombreCanton))

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_EMPRESA_ID))
                val nombre = getString(getColumnIndexOrThrow(COLUMN_EMPRESA_NOMBRE))
                val imagen = getBlob(getColumnIndexOrThrow(COLUMN_EMPRESA_IMAGEN))
                val slogan = getString(getColumnIndexOrThrow(COLUMN_EMPRESA_SLOGAN))
                val video = getBlob(getColumnIndexOrThrow(COLUMN_EMPRESA_VIDEO))
                val nombrePropietario =
                    getString(getColumnIndexOrThrow(COLUMN_EMPRESA_NOMBRE_PROPIETARIO))
                val imagenPropietario =
                    getBlob(getColumnIndexOrThrow(COLUMN_EMPRESA_IMAGEN_PROPIETARIO))
                val facebook = getString(getColumnIndexOrThrow(COLUMN_EMPRESA_FACEBOOK))
                val instagram = getString(getColumnIndexOrThrow(COLUMN_EMPRESA_INSTAGRAM))
                val whatsapp = getString(getColumnIndexOrThrow(COLUMN_EMPRESA_WHATSAPP))
                val idCanton = getLong(getColumnIndexOrThrow(COLUMN_EMPRESA_ID_CANTON))

                empresas.add(
                    Empresa(
                        id,
                        nombre,
                        imagen,
                        slogan,
                        video,
                        nombrePropietario,
                        imagenPropietario,
                        facebook,
                        instagram,
                        whatsapp,
                        idCanton
                    )
                )
            }
        }
        cursor.close()
        return empresas
    }

    fun getAllProductosByEmpresa(idEmpresa: Long): List<Producto> {
        val productos = mutableListOf<Producto>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PRODUCTO,
            arrayOf(
                COLUMN_PRODUCTO_ID,
                COLUMN_PRODUCTO_NOMBRE,
                COLUMN_PRODUCTO_IMAGEN,
                COLUMN_PRODUCTO_PRECIO,
                COLUMN_PRODUCTO_DESCRIPCION,
                COLUMN_PRODUCTO_ID_EMPRESA
            ),
            "$COLUMN_PRODUCTO_ID_EMPRESA = ?",
            arrayOf(idEmpresa.toString()),
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_PRODUCTO_ID))
                val nombre = getString(getColumnIndexOrThrow(COLUMN_PRODUCTO_NOMBRE))
                val imagen = getBlob(getColumnIndexOrThrow(COLUMN_PRODUCTO_IMAGEN))
                val precio = getDouble(getColumnIndexOrThrow(COLUMN_PRODUCTO_PRECIO))
                val descripcion = getString(getColumnIndexOrThrow(COLUMN_PRODUCTO_DESCRIPCION))

                productos.add(
                    Producto(
                        id,
                        nombre,
                        imagen,
                        precio,
                        descripcion,
                        idEmpresa
                    )
                )
            }
        }
        cursor.close()
        return productos
    }

    fun insertEmpresa(empresa: Empresa): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMPRESA_NOMBRE, empresa.nombre)
            put(COLUMN_EMPRESA_IMAGEN, empresa.imagenEmpresa)
            put(COLUMN_EMPRESA_SLOGAN, empresa.slogan)
            put(COLUMN_EMPRESA_VIDEO, empresa.video)
            put(COLUMN_EMPRESA_NOMBRE_PROPIETARIO, empresa.nombrePropietario)
            put(COLUMN_EMPRESA_IMAGEN_PROPIETARIO, empresa.imagenPropietario)
            put(COLUMN_EMPRESA_FACEBOOK, empresa.facebook)
            put(COLUMN_EMPRESA_INSTAGRAM, empresa.instagram)
            put(COLUMN_EMPRESA_WHATSAPP, empresa.whatsapp)
            put(COLUMN_EMPRESA_ID_CANTON, empresa.idCanton)
        }

        return db.insert(TABLE_EMPRESA, null, values)
    }

    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun getBitmapFromEmpresa(idEmpresa: Long): Bitmap? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_EMPRESA,
            arrayOf(COLUMN_EMPRESA_IMAGEN),
            "$COLUMN_EMPRESA_ID = ?",
            arrayOf(idEmpresa.toString()),
            null,
            null,
            null
        )

        var bitmap: Bitmap? = null

        with(cursor) {
            if (moveToFirst()) {
                val imagenByteArray = getBlob(getColumnIndexOrThrow(COLUMN_EMPRESA_IMAGEN))
                bitmap = byteArrayToBitmap(imagenByteArray)
            }
        }

        cursor.close()
        return bitmap
    }

    fun getBitmapFromPropietario(idEmpresa: Long): Bitmap? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_EMPRESA,
            arrayOf(COLUMN_EMPRESA_IMAGEN_PROPIETARIO),
            "$COLUMN_EMPRESA_ID = ?",
            arrayOf(idEmpresa.toString()),
            null,
            null,
            null
        )

        var bitmap: Bitmap? = null

        with(cursor) {
            if (moveToFirst()) {
                val imagenPropietarioByteArray = getBlob(getColumnIndexOrThrow(COLUMN_EMPRESA_IMAGEN_PROPIETARIO))
                bitmap = byteArrayToBitmap(imagenPropietarioByteArray)
            }
        }

        cursor.close()
        return bitmap
    }

    fun getBitmapFromVideo(idEmpresa: Long): Bitmap? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_EMPRESA,
            arrayOf(COLUMN_EMPRESA_VIDEO),
            "$COLUMN_EMPRESA_ID = ?",
            arrayOf(idEmpresa.toString()),
            null,
            null,
            null
        )

        var bitmap: Bitmap? = null

        with(cursor) {
            if (moveToFirst()) {
                val videoByteArray = getBlob(getColumnIndexOrThrow(COLUMN_EMPRESA_VIDEO))
                bitmap = byteArrayToBitmap(videoByteArray)
            }
        }

        cursor.close()
        return bitmap
    }


}
