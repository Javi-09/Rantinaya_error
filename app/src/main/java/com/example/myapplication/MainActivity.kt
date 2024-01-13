package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.e_commerce.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            findViewById(R.id.toolbar),
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
        }

        val btnOpenWebsite = findViewById<Button>(R.id.btnOpenWebsite)
        btnOpenWebsite.setOnClickListener {
            openWebsite()
        }
    }

    private fun handleNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            //Redireccionamiento PRODUCTO

            //Producto Francisco de Orellana
            R.id.subitem_orellanaP -> {
                showToast("Accediste a Francisco de Orellana")
                startActivity(Intent(this, MostrarEmpresaFranciscoOrellanaProducto::class.java))
                return true
            }

            //Producto Joya de los Sachas
            R.id.subitem_sachaP -> {
                showToast("Accediste a Joya de los Sachas")
                startActivity(Intent(this, MostrarEmpresaSachaProducto::class.java))
                return true
            }

            //Producto Loreto
            R.id.subitem_loretoP -> {
                showToast("Accediste a Loreto")
                startActivity(Intent(this, MostrarEmpresaLoretoProducto::class.java))
                return true
            }

            //Producto Aguarico
            R.id.subitem_aguaricoP -> {
                showToast("Accediste a Aguarico")
                startActivity(Intent(this, MostrarEmpresaAguaricoProducto::class.java))
                return true
            }

            //Redireccionamiento SERVICIO

            //Servicio Francisco de Orellana
            R.id.subitem_orellanaS -> {
                showToast("Accediste a Francisco de Orellana")
                startActivity(Intent(this, MostrarEmpresaFranciscoOrellanaServicio::class.java))
                return true
            }

            //Servicio Joya de los Sachas
            R.id.subitem_sachaS -> {
                showToast("Accediste a Joya de los Sachas")
                startActivity(Intent(this, MostrarEmpresaSachaServicio::class.java))
                return true
            }

            //Servicio Loreto
            R.id.subitem_loretoS -> {
                showToast("Accediste a Loreto")
                startActivity(Intent(this, MostrarEmpresaLoretoServicio::class.java))
                return true
            }

            //Servicio Aguarico
            R.id.subitem_aguaricoS -> {
                showToast("Accediste a Aguarico")
                startActivity(Intent(this, MostrarEmpresaAguaricoServicio::class.java))
                return true
            }

            //ACCESO A SERVICIOS
            R.id.nav_servicios -> {
                showToast("Accediste a Servicios")
            }

            //Redireccionamiento ACERCA DE
            R.id.nav_acercaDe -> {
                showToast("Accediste a Acerca de")
                startActivity(Intent(this, AcercaDe::class.java))
                return true
            }

            // Redireccionamiento ADMINISTRADOR
            R.id.nav_admin -> {
                showToast("Accediste a Administrador")
                startActivity(Intent(this, Administrador::class.java))
                return true
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openWebsite() {
        val websiteUrl = "https://www.rantinaya.com/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showToast("No se pudo encontrar una aplicación para abrir el enlace.")
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }
}
