package com.example.anmp_hobbyapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.anmp_hobbyapp.R
import com.example.anmp_hobbyapp.databinding.ActivityMainBinding
import com.example.anmp_hobbyapp.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

//    PUNYA REVA RAYHANSYAHRONI NADITYAPUTRA - 160421103

    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)

        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.visibility = View.INVISIBLE

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
//    PUNYA REVA RAYHANSYAHRONI NADITYAPUTRA - 160421103

    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp()
        return NavigationUI.navigateUp(navController, binding.drawerLayout) || super.onSupportNavigateUp()
    }

    fun showBottomNavDrawer() {
        binding.bottomNav.visibility = View.VISIBLE
        binding.navView.visibility = View.VISIBLE
    }

    fun hideBottomNavDrawer() {
        binding.bottomNav.visibility = View.INVISIBLE
        binding.navView.visibility = View.INVISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}