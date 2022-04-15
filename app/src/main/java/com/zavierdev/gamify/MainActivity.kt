package com.zavierdev.gamify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zavierdev.gamify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appbarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.appBarMain.topToolbar)
        appbarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_favorite
            ),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appbarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appbarConfiguration) || super.onSupportNavigateUp()
    }
}