package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R


class AcercaDe : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_miembros_equipo)

        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        val adapter=CustomAdapter()

        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
}