package com.example.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.base.BaseActivty
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.ActivityMainBinding
import com.example.utilis.goneIf
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
        manageBottomNavVisiabilty()
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment?.navController
        binding?.bottomNavView?.setupWithNavController(navController!!)


    }

    private fun manageBottomNavVisiabilty() {
        navController?.addOnDestinationChangedListener { _, destnation, _ ->
            binding?.bottomNavView?.goneIf(destnation.id == R.id.articleFragment || destnation.id == R.id.sourceFragment)
        }
    }


}