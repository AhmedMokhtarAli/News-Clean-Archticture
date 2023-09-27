package com.example.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.base.BaseActivty
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.ActivityMainBinding
import com.example.utilis.printToLogD
import com.example.utilis.setTransparentStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : /*BaseActivty(R.layout.activity_main)*/ AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private var navController: NavController? = null
    private var navHostFragment: NavHostFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupNavigation()
        setTransparentStatusBar()
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment?.navController
        binding?.bottomNavView?.setupWithNavController(navController!!)

        navController?.addOnDestinationChangedListener{_,destnation,_ ->
            destnation.id.toString().printToLogD("llllllllllllllllll")
        }
    }
}