package com.example.myapplication
import com.example.myapplication.R
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

import android.widget.TextView
import android.widget.VideoView
import com.example.myapplication.database.DatabaseHelperProducto
import com.example.myapplication.database.Empresa

class EmpresaAdapter(context: Context, empresas: List<Empresa>) :
    ArrayAdapter<Empresa>(context, 0, empresas) {

    private val dbHelper = DatabaseHelperProducto(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.list_item_empresa,
                parent,
                false
            )
        }

        val currentEmpresa = getItem(position)

        val nombreTextView: TextView = listItemView!!.findViewById(R.id.nombreTextView)
        nombreTextView.text = currentEmpresa?.nombre

        val imagenEmpresaImageView: ImageView =
            listItemView.findViewById(R.id.imagenEmpresaImageView)
        val imagenEmpresa = dbHelper.getBitmapFromEmpresa(currentEmpresa?.id ?: -1)
        if (imagenEmpresa != null) {
            imagenEmpresaImageView.setImageBitmap(imagenEmpresa)
        } else {
            // Puedes establecer una imagen predeterminada o dejarlo vacío según tus necesidades
            imagenEmpresaImageView.setImageResource(R.drawable.ic_launcher_foreground)
        }

        val sloganTextView: TextView = listItemView.findViewById(R.id.sloganTextView)
        sloganTextView.text = currentEmpresa?.slogan

        val videoView: VideoView = listItemView.findViewById(R.id.videoView)
        // Obtén la miniatura del video como un marcador de posición
        val videoThumbnail: Bitmap? =
            dbHelper.getBitmapFromVideo(currentEmpresa?.id ?: -1)
        if (videoThumbnail != null) {
            // Configura el bitmap como vista previa del video
            videoView.setImageBitmap(videoThumbnail)
        } else {
            // Puedes establecer una imagen predeterminada o dejarlo vacío según tus necesidades
            videoView.setImageResource(R.drawable.ic_launcher_foreground)
        }

        return listItemView
    }
}
