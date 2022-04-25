package com.example.rickandmorty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RootActivity : AppCompatActivity() {

    private val vm: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                vm.isLoading.value
            }
        }
        setContentView(R.layout.activity_root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_navigator) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.charactersFragment,
                R.id.locationsFragment,
                R.id.episodesFragment
            )
        )

        bottomNavigationView.setupWithNavController(navController)
    }
}