package com.example.tinkoffkinopoiskapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tinkoffkinopoiskapi.R
import com.example.tinkoffkinopoiskapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host)

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_fragment_holder, PopularFilmsFragment.newInstance())
//            .commit()
    }
}