package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.DatabaseHelperProducto
import com.example.myapplication.database.Empresa
import com.example.myapplication.database.Canton

class IngresoProducto : AppCompatActivity() {

    private lateinit var editTextNombreEmpresa: EditText
    private lateinit var editTextSlogan: EditText
    private lateinit var editTextNombrePropietario: EditText
    private lateinit var editTextFacebook: EditText
    private lateinit var editTextInstagram: EditText
    private lateinit var editTextWhatsapp: EditText
    private lateinit var spinnerCanton: Spinner
    private lateinit var btnGuardarEmpresa: Button
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnSeleccionarImagenPropietario: Button
    private lateinit var btnSeleccionarVideo: Button

    private lateinit var databaseHelper: DatabaseHelperProducto

    private var imagenSeleccionada: ByteArray? = null
    private var videoSeleccionado: ByteArray? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                if (isImage(uri)) {
                    val inputStream = contentResolver.openInputStream(uri)
                    imagenSeleccionada = inputStream?.readBytes()
                } else if (isVideo(uri)) {
                    val inputStream = contentResolver.openInputStream(uri)
                    videoSeleccionado = inputStream?.readBytes()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_producto)

        editTextNombreEmpresa = findViewById(R.id.editTextNombreEmpresa)
        spinnerCanton = findViewById(R.id.spinnerCanton)
        editTextSlogan = findViewById(R.id.editTextSlogan)
        editTextNombrePropietario = findViewById(R.id.editTextNombrePropietario)
        editTextFacebook = findViewById(R.id.editTextFacebook)
        editTextInstagram = findViewById(R.id.editTextInstagram)
        editTextWhatsapp = findViewById(R.id.editTextWhatsapp)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnSeleccionarImagenPropietario = findViewById(R.id.btnSeleccionarImagenPropietario)
        btnSeleccionarVideo = findViewById(R.id.btnSeleccionarVideo)
        btnGuardarEmpresa = findViewById(R.id.btnGuardarEmpresa)

        databaseHelper = DatabaseHelperProducto(this)

        val cantones = databaseHelper.getAllCantones()
        val cantonAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cantones)
        cantonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCanton.adapter = cantonAdapter

        btnSeleccionarImagen.setOnClickListener {
            getContent.launch("image/*")
        }

        btnSeleccionarImagenPropietario.setOnClickListener {
            getContent.launch("image/*")
        }

        btnSeleccionarVideo.setOnClickListener {
            getContent.launch("video/*")
        }

        btnGuardarEmpresa.setOnClickListener {
            guardarEmpresa()
        }
    }

    private fun guardarEmpresa() {
        if (imagenSeleccionada != null && videoSeleccionado != null) {
            val empresa = Empresa(
                nombre = editTextNombreEmpresa.text.toString(),
                imagenEmpresa = imagenSeleccionada!!,
                slogan = editTextSlogan.text.toString(),
                video = videoSeleccionado!!,
                nombrePropietario = editTextNombrePropietario.text.toString(),
                imagenPropietario = imagenSeleccionada!!,
                facebook = editTextFacebook.text.toString(),
                instagram = editTextInstagram.text.toString(),
                whatsapp = editTextWhatsapp.text.toString(),
                idCanton = (spinnerCanton.selectedItem as Canton).id
            )

            val empresaId = databaseHelper.insertEmpresa(empresa)

            if (empresaId != -1L) {
                mostrarMensaje("Empresa guardada exitosamente")
            } else {
                mostrarMensaje("Error al guardar la empresa. Inténtalo de nuevo.")
            }
        } else {
            mostrarMensaje("Debes seleccionar tanto una imagen como un video para guardar la empresa.")
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun isImage(uri: Uri): Boolean {
        val type = contentResolver.getType(uri)
        return type?.startsWith("image/") == true
    }

    private fun isVideo(uri: Uri): Boolean {
        val type = contentResolver.getType(uri)
        return type?.startsWith("video/") == true
    }
}
