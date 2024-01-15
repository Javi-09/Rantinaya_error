package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    val names = arrayOf("Fernando Chito",
        "Génesis Ortega",
        "Mishell Mungabusi",
        "Aracely Maza",
        "Julissa Gongora",
        "Grace Reasco",
        "Luiz Ruiz",
        "Paúl Pupiales",
        "Anderson Verdezoto")
    val details = arrayOf("Scrum Master del proyecto",
        "Diseñador UI/UX ",
        "Gestor de proyectos ",
        "Analista QA ",
        "Documentador ",
        "Desarrollador ",
        "Stakeholder ",
        "Desarrollador ",
        "Analista ")
    val image = intArrayOf(R.drawable.fernando,
        R.drawable.genesis,
        R.drawable.mishell,
        R.drawable.aracely,
        R.drawable.julissa,
        R.drawable.grace,
        R.drawable.luiz,
        R.drawable.paul,
        R.drawable.anderson)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.itemName.text= names[i]
        viewHolder.itemDetail.text= details[i]
        viewHolder.itemImage.setImageResource(image[i])
    }

    override fun getItemCount(): Int {
        return names.size
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var itemImage:ImageView
        var itemName: TextView
        var itemDetail:TextView

        init{
            itemImage = itemView.findViewById(R.id.item_image)
            itemName = itemView.findViewById(R.id.item_name)
            itemDetail = itemView.findViewById(R.id.item_detail)

        }
    }

}