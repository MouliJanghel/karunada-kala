package com.example.karunada_kala

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.karunada_kala.databinding.ActivityMainBinding
import com.example.karunada_kala.utils.FirestoreUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Seed sample data to Firestore on first run for demonstration purposes
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("is_first_run", true)) {
            FirestoreUtils.seedSampleData()
            sharedPref.edit {
                putBoolean("is_first_run", false)
            }
        }

        // Initialize Toolbar
        setSupportActionBar(binding.mainToolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        // Destinations that should not show a back button
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.explorerFragment, R.id.mapFragment, R.id.eventsFragment)
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        // Navigation state management: Control visibility of UI components
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    binding.appBarLayout.visibility = View.GONE
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.detailFragment -> {
                    // Detail screen uses its own CollapsingToolbarLayout, hide main app bar
                    binding.appBarLayout.visibility = View.GONE
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.workshopSignupFragment -> {
                    binding.appBarLayout.visibility = View.VISIBLE
                    binding.bottomNavigation.visibility = View.GONE
                }
                else -> {
                    binding.appBarLayout.visibility = View.VISIBLE
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
