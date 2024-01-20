package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Empresa
import com.example.myapplication.database.Canton

class IngresarEmpresaProducto : AppCompatActivity() {

    private lateinit var editTextNombreEmpresa: EditText
    private lateinit var editTextSlogan: EditText
    private lateinit var editTextNombrePropietario: EditText
    private lateinit var editTextFacebook: EditText
    private lateinit var editTextInstagram: EditText
    private lateinit var editTextWhatsapp: EditText
    private lateinit var editLatitud: EditText
    private lateinit var editLongitud: EditText
    private lateinit var editVideoUrl: EditText  // Nuevo campo para la URL del video
    private lateinit var spinnerCanton: Spinner
    private lateinit var btnGuardarEmpresa: Button
    private lateinit var btnSeleccionarImagenEmpresa: Button
    private lateinit var btnSeleccionarImagenPropietario: Button

    private lateinit var databaseHelper: DBHelperProducto
    private var imagenSeleccionadaEmpresa: ByteArray? = null
    private var imagenSeleccionadaPropietario: ByteArray? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                if (isImage(uri)) {
                    val inputStream = contentResolver.openInputStream(uri)
                    if (btnSeleccionarImagenPropietario.isActivated) {
                        imagenSeleccionadaPropietario = inputStream?.readBytes()
                    } else {
                        imagenSeleccionadaEmpresa = inputStream?.readBytes()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_empresa_producto)

        editTextNombreEmpresa = findViewById(R.id.editTextNombreEmpresa)
        spinnerCanton = findViewById(R.id.spinnerCanton)
        editTextSlogan = findViewById(R.id.editTextSlogan)
        editTextNombrePropietario = findViewById(R.id.editTextNombrePropietario)
        editTextFacebook = findViewById(R.id.editTextFacebook)
        editTextInstagram = findViewById(R.id.editTextInstagram)
        editTextWhatsapp = findViewById(R.id.editTextWhatsapp)
        editLatitud = findViewById(R.id.editLatitud)
        editLongitud = findViewById(R.id.editLongitud)
        editVideoUrl = findViewById(R.id.editVideoUrl)  // Referencia al EditText de la URL del video
        btnSeleccionarImagenEmpresa = findViewById(R.id.btnSeleccionarImagenEmpresa)
        btnSeleccionarImagenPropietario = findViewById(R.id.btnSeleccionarImagenPropietario)
        btnGuardarEmpresa = findViewById(R.id.btnGuardarEmpresa)

        databaseHelper = DBHelperProducto(this)

        val cantones = databaseHelper.getAllCantones()
        val cantonAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cantones)
        cantonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCanton.adapter = cantonAdapter

        btnSeleccionarImagenEmpresa.setOnClickListener {
            btnSeleccionarImagenPropietario.isActivated = false
            getContent.launch("image/*")
        }

        btnSeleccionarImagenPropietario.setOnClickListener {
            btnSeleccionarImagenPropietario.isActivated = true
            getContent.launch("image/*")
        }

        btnGuardarEmpresa.setOnClickListener {
            guardarEmpresa()
        }
    }

    private fun guardarEmpresa() {
        val empresa = Empresa(
            nombre = editTextNombreEmpresa.text.toString(),
            slogan = editTextSlogan.text.toString(),
            nombrePropietario = editTextNombrePropietario.text.toString(),
            facebook = editTextFacebook.text.toString(),
            instagram = editTextInstagram.text.toString(),
            whatsapp = editTextWhatsapp.text.toString(),
            longitud = editLatitud.text.toString().toDouble(),
            latitud = editLongitud.text.toString().toDouble(),
            imagen_empresa = imagenSeleccionadaEmpresa,
            imagen_propietario = imagenSeleccionadaPropietario,
            video_url = editVideoUrl.text.toString(),  // Nueva propiedad para la URL del video
            fkEmpresaCanton = (spinnerCanton.selectedItem as Canton).id
        )

        val empresaId = databaseHelper.insertEmpresa(empresa)

        if (empresaId != -1L) {
            mostrarMensaje("Empresa guardada exitosamente")
        } else {
            mostrarMensaje("Error al guardar la empresa. Inténtalo de nuevo.")
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun isImage(uri: Uri): Boolean {
        val type = contentResolver.getType(uri)
        return type?.startsWith("image/") == true
    }
}
