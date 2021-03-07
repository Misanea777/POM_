package com.example.mytrashyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)

        setupNavDrawer()

    }


    fun setupNavDrawer() {
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        findViewById<Toolbar>(R.id.toolbar)
                .setupWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view)
                .setupWithNavController(navController)
    }


//    override fun onClick(v: View) {
//        val action =
//                NavHostFragmentDirections
//                        .actionNavHostFragmentToBlankFragment3()
//        v.findNavController().navigate(action)
//    }





}