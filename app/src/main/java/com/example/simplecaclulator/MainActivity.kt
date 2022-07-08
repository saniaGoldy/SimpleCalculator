package com.example.simplecaclulator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simplecaclulator.databinding.ActivityMainBinding

const val TAG = "MyApp"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
