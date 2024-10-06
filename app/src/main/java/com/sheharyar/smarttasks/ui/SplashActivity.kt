package com.sheharyar.smarttasks.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sheharyar.smarttasks.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Set the theme before calling super.onCreate
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Set the layout

        // Start MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        // Finish SplashActivity so the user can't return to it
        finish()
    }
}