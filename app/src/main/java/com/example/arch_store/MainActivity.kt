package com.example.arch_store

import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toolbar: Toolbar;
    lateinit var bottomNav: BottomNavigationView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

//        appBarConfiguration = AppBarConfiguration(navController.graph)

        appBarConfiguration =AppBarConfiguration
            .Builder(
                R.id.home_dest,
                R.id.search_dest,
                R.id.profile_dest,
                R.id.cart_dest
            )
            .build()

        // TODO STEP 9.5 - Create an AppBarConfiguration with the correct top-level destinations
        // You should also remove the old appBarConfiguration setup above
//        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.home_dest, R.id.deeplink_dest)
//            )
        // TODO END STEP 9.5

        setupActionBar(navController, appBarConfiguration)


        setupBottomNavMenu(navController)

        bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        navController.addOnDestinationChangedListener { _, destination, _ ->


//            show bottom nav according to destination
            when (destination.id) {
                R.id.splashFragment -> {
                    bottomNav.visibility = View.GONE
                    toolbar.visibility = View.GONE
//                    waitForSplashScreen(bottomNav, toolbar);
                }
                R.id.signInFragment -> {
                    bottomNav.visibility = View.GONE
                    toolbar.visibility = View.GONE
//                    waitForSplashScreen(bottomNav, toolbar);
                }
                R.id.signUpFragment -> {
                    bottomNav.visibility = View.GONE
                    toolbar.visibility = View.GONE
//                    waitForSplashScreen(bottomNav, toolbar);
                }
                R.id.cart_dest -> {
                    bottomNav.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                }
                R.id.search_dest -> {
                    bottomNav.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                }
                R.id.home_dest -> {
                    bottomNav.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                }
                R.id.profile_dest -> {
                    bottomNav.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                }
                R.id.productDetailFragment -> {
                    bottomNav.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                }


            }


            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
        }
    }


    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }


    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {

        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }


}