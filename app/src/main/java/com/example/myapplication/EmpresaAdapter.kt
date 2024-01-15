package com.example.myapplication

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.Empresa

class EmpresaAdapter(val empresas: List<Empresa>, private val clickListener: ClickListener? = null) : RecyclerView.Adapter<EmpresaAdapter.EmpresaViewHolder>() {

    interface ClickListener {
        fun onVerEmpresaClick(position: Int)
    }

    // Exponer la lista de empresas como propiedad pública
    val empresasList: List<Empresa>
        get() = empresas

    class EmpresaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textNombre)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imageEmpresa)
        val sloganTextView: TextView = itemView.findViewById(R.id.textSlogan)
        val btnRedirect: Button = itemView.findViewById(R.id.btnRedirect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empresa, parent, false)
        return EmpresaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int) {
        val empresa = empresas[position]
        holder.nombreTextView.text = empresa.nombre
        holder.sloganTextView.text = empresa.slogan

        // Verificar si el ByteArray de la imagen no está vacío
        if (empresa.imagen_empresa?.isNotEmpty() == true) {
            // Convertir ByteArray a Bitmap
            val bitmap = BitmapFactory.decodeByteArray(empresa.imagen_empresa, 0, empresa.imagen_empresa.size)

            // Cargar Bitmap en ImageView
            holder.imagenImageView.setImageBitmap(bitmap)
        } else {
            // Si el ByteArray de la imagen está vacío, dejar el ImageView vacío
            holder.imagenImageView.setImageDrawable(null)
        }

        // Configurar clic del botón solo si clickListener no es nulo
        holder.btnRedirect.setOnClickListener {
            clickListener?.onVerEmpresaClick(position)
        }
    }

    override fun getItemCount(): Int {
        return empresas.size
    }
}
