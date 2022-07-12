package com.example.newsapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "NewsApp"
        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (FirebaseAuth.getInstance().currentUser == null)
        navController.navigate(R.id.loginFragment) //Запуск страницы для авторизации
        //FirebaseAuth - хранит информацию об авторизации
        // currentUser - получить текущего юзера


            //if (!Prefs(this).iShown())
            //если onBoard не показан, запусти boardFragment
        navController.navigate(R.id.boardFragment)
            // Запуск boardFragment

        navController.addOnDestinationChangedListener { navController: NavController, navDestination: NavDestination, bundle: Bundle? ->
            val fragments = arrayListOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )

            if (fragments.contains(navDestination.id))
                binding.navView.visibility = View.VISIBLE
            else
                binding.navView.visibility = View.GONE

            if (navDestination.id == R.id.boardFragment)
                supportActionBar?.hide()
            else
                supportActionBar?.show()
        }
        }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    }
