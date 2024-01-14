package com.example.dmaiseu

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.dmaiseu.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar:Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener (this)

        val toogle = ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        navigationView.setCheckedItem(R.id.nav_perfil)

       if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_perfil)
        }
        val sharedPrefs: SharedPreferences = this.getSharedPreferences("SHARED_PREFS_USER", Context.MODE_PRIVATE)
        val user_name_drawer : TextView = findViewById<NavigationView?>(R.id.nav_view).getHeaderView(0).findViewById(R.id.user_name_drawer)
        user_name_drawer.text = sharedPrefs.getString("user_name",null)!!.split(" ")[0]
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_perfil -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,ProfileFragment()).commit()
            R.id.nav_Tel -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,PhoneFragment()).commit()
            R.id.nav_casa -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,HouseFragment()).commit()
            R.id.nav_email -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,EmailFragment()).commit()
            R.id.nav_Info -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,InfoFragment()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,SettingsFragment()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }


}