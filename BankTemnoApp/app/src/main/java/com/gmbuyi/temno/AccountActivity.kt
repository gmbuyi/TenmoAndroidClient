package com.gmbuyi.temno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gmbuyi.temno.databinding.AccountLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * the activity that hosts the fragments
 *
 */

class AccountActivity : AppCompatActivity() {


    private lateinit var binding: AccountLayoutBinding
      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

          supportActionBar?.hide()
          binding = AccountLayoutBinding.inflate(layoutInflater)
          setContentView(binding.root)





          val navView: BottomNavigationView = binding.navView


         val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_send, R.id.navigation_request
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}