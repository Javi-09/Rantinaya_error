package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Empresa
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.Charset

class DetalleEmpresaProducto : AppCompatActivity() {

    private lateinit var imageViewEmpresa: ImageView
    private lateinit var textViewNombre: TextView
    private lateinit var textViewPropietario: TextView
    private lateinit var btnVerMapa: Button
    private lateinit var imageViewFacebook: ImageView
    private lateinit var imageViewInstagram: ImageView
    private lateinit var imageViewWhatsapp: ImageView
    private lateinit var videoViewEmpresa: VideoView

    private lateinit var databaseHelper: DBHelperProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_empresa_producto)

        // Inicializar vistas
        imageViewEmpresa = findViewById(R.id.imageViewPropietario)
        textViewNombre = findViewById(R.id.textViewNombreEmpresa)
        textViewPropietario = findViewById(R.id.textViewNombrePropietario)
        btnVerMapa = findViewById(R.id.btnVerMapa)
        imageViewFacebook = findViewById(R.id.imageViewFacebook)
        imageViewInstagram = findViewById(R.id.imageViewInstagram)
        imageViewWhatsapp = findViewById(R.id.imageViewWhatsapp)
        videoViewEmpresa = findViewById(R.id.videoViewEmpresa)

        // Inicializar DBHelper-
        databaseHelper = DBHelperProducto(this)

        // Obtener ID de la empresa desde el Intent (ajusta según cómo pasas los datos)
        val empresaId = intent.getLongExtra("empresa_id", -1)

        // Obtener datos de la empresa desde la base de datos
        val empresa = databaseHelper.getEmpresaById(empresaId)

        // Mostrar datos en las vistas
        mostrarDetallesEmpresa(empresa)
    }

    private fun mostrarDetallesEmpresa(empresa: Empresa?) {
        if (empresa != null) {
            // Puedes implementar lógica para cargar la imagen, el mapa, etc.
            // Por ahora, simplemente mostramos algunos detalles
            textViewNombre.text = empresa.nombre
            textViewPropietario.text = empresa.nombrePropietario

            // Cargar imagen en ImageView
            // Cargar imagen del propietario en imageViewPropietario
            if (empresa.imagen_propietario != null && empresa.imagen_propietario.isNotEmpty()) {
                // Convertir ByteArray a Bitmap
                val bitmap =
                    BitmapFactory.decodeByteArray(empresa.imagen_propietario, 0, empresa.imagen_propietario.size)

                // Cargar Bitmap en ImageView
                imageViewEmpresa.setImageBitmap(bitmap)
            } else {
                // Si el ByteArray de la imagen está vacío, dejar el ImageView vacío
                imageViewEmpresa.setImageDrawable(null)
            }

            // Reproducir video en VideoView
            /*
            if (empresa.video_empresa != null && empresa.video_empresa.isNotEmpty()) {
                // Convertir ByteArray a InputStream
                val videoInputStream = ByteArrayInputStream(empresa.video_empresa)

                // Configurar el origen del video en el VideoView desde el InputStream
                videoViewEmpresa.setVideoURI(Uri.parse(videoInputStream.toString()))

                // Iniciar la reproducción del video
                videoViewEmpresa.setOnPreparedListener { mp ->
                    mp.isLooping = true // Opcional: Hacer el video en bucle si es necesario
                }
                videoViewEmpresa.start()
            } else {
                // Si el ByteArray del video está vacío, puedes ocultar o manejar de alguna manera el VideoView
                videoViewEmpresa.visibility = View.GONE
            }*/

            //-------------------




            // Configurar clic del botón "Ver en Mapa"
            btnVerMapa.setOnClickListener {
                // Lógica para abrir el mapa, puedes usar la latitud y longitud de la empresa
                // por ejemplo, abrir Google Maps con las coordenadas
            }

            // Puedes configurar clics en los iconos de redes sociales de manera similar
            // por ejemplo, abrir la aplicación correspondiente al clickear el icono de WhatsApp

            // Asegúrate de implementar lógica para cargar la imagen, el mapa y otros detalles según tus necesidades

            //Redireccionamiento ICONOS
            //Redireccionamiento Facebook Icono
            imageViewFacebook.setOnClickListener {
                // Aquí obtienes la URL de Facebook de tu objeto Empresa
                val urlFacebook = empresa?.facebook

                // Verificar si la URL no está vacía
                if (!urlFacebook.isNullOrBlank()) {
                    // Crear un Intent para abrir la URL en un navegador
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFacebook))

                    // Verificar si hay una aplicación que pueda manejar este Intent
                    if (intent.resolveActivity(packageManager) != null) {
                        // Abrir la URL en el navegador o la aplicación de Facebook
                        startActivity(intent)
                    } else {
                        // Manejar el caso donde no hay aplicación para manejar la URL
                        Log.e("DetalleEmpresaProducto", "No se encontró una aplicación para manejar la URL.")
                    }
                } else {
                    // Manejar el caso donde la URL está vacía o nula
                    Log.e("DetalleEmpresaProducto", "La URL de Facebook está vacía o nula.")
                }
            }
            //-----------------

            //Redireccionamiento Instagram
            imageViewInstagram.setOnClickListener {
                val urlInstagram = empresa?.instagram

                if (!urlInstagram.isNullOrBlank()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlInstagram))

                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e("DetalleEmpresaProducto", "No se encontró una aplicación para manejar la URL de Instagram.")
                    }
                } else {
                    Log.e("DetalleEmpresaProducto", "La URL de Instagram está vacía o nula.")
                }
            }
            //--------------------

            //Redireccionamiento WhatsApp
            imageViewWhatsapp.setOnClickListener {
                val phoneNumber = empresa?.whatsapp

                if (!phoneNumber.isNullOrBlank()) {
                    // Crear un Intent para abrir la aplicación de WhatsApp con el número de teléfono
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber"))

                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e("DetalleEmpresaProducto", "No se encontró una aplicación para manejar la URL de WhatsApp.")
                    }
                } else {
                    Log.e("DetalleEmpresaProducto", "El número de teléfono de WhatsApp está vacío o nulo.")
                }
            }


            //--------------------




        }
    }

    override fun onPause() {
        super.onPause()
        videoViewEmpresa.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoViewEmpresa.stopPlayback()
    }
}
