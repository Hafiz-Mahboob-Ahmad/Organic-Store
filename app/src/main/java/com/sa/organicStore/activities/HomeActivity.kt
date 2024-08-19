package com.sa.organicStore.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sa.organicStore.R
import com.sa.organicStore.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavController()
        setupBottomNavigationView()
        hideBottomNavBar()
    }

    private fun setupNavController() {
        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_nav_host_fragment_container) as NavHostFragment
        navController = navHost.navController
    }

    private fun setupBottomNavigationView() {
        binding.bnvContainer.setupWithNavController(navController)
    }

    private fun hideBottomNavBar() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.product_fragment ||
                destination.id == R.id.bundle_details_fragment ||
                destination.id == R.id.cart_fragment ||
                destination.id == R.id.search_fragment
            ) {
                binding.bnvContainer.visibility = View.GONE
                binding.fabStore.visibility = View.GONE
            } else {
                binding.bnvContainer.visibility = View.VISIBLE
                binding.fabStore.visibility = View.VISIBLE
            }
        }
    }
}