package com.example.appturismo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import android.widget.PopupMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val bottomNavigationBar:NavHostFragment = findViewById<Any>(R.id.menuBottomBar)//findViewById<BottomNavigationView>(R.id.menuBottomBar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val bottomNavigationBar = findViewById<SmoothBottomBar>(R.id.menuBottomBar)
        val navController = navHostFragment.navController
        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu: Menu = popupMenu.menu
        bottomNavigationBar.setupWithNavController(menu,navController)
    }
}